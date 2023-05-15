package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import seuleuleug.domain.challenges.*;
import seuleuleug.domain.hospital.PageDto;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChallengesService {
    @Autowired
    ChallengesEntityRepository challengesEntityRepository;
    @Autowired
    ChallengesImgEntityRepositoty challengesImgEntityRepositoty;
    @Autowired
    FileService fileService;
    @Autowired
    ChallengeResultsEntityRepository challengeResultsEntityRepository;
    @Autowired
    MemberEntityRepository memberEntityRepository;


    @Transactional
    public PageDto get(PageDto pageDto){
        Pageable pageable = PageRequest.of(pageDto.getPage()-1,6, Sort.by(Sort.Direction.DESC,"chno"));

        Page<ChallengesEntity> entityPage = challengesEntityRepository.findAll( pageable );

        List<ChallengesDto> challengesDtoList = new ArrayList<>();
        entityPage.forEach((b)->{
            List<FileDto> list = new ArrayList<>();
            b.getChallengesImgEntitiy().forEach((r)->{
                list.add( r.toDto() );
            });

            ChallengesDto dto = b.todto();
            dto.setChfiles(list);
            challengesDtoList.add(dto);

        });
        pageDto.setChallengesDtoList(challengesDtoList);
        pageDto.setTotalPage(entityPage.getTotalPages());
        pageDto.setTotalCount(entityPage.getTotalElements());
        log.info("Challenges"+pageDto);
        return pageDto;
    }

    @Transactional
    public boolean post(ChallengesDto challengesDto){
        log.info("Posting challenge"+challengesDto);
        ChallengesEntity challengesEntity = challengesEntityRepository.save(challengesDto.toEntity());

        if( challengesDto.getChimg().size() != 0 ){
            // 하나씩 업로드
            challengesDto.getChimg().forEach( (img)->{
                // 업로드된 파일 결과[리턴]
                FileDto fileDto = fileService.fileupload( img );
                // DB 저장
                ChallengesImgEntity challengesImgEntity = challengesImgEntityRepositoty.save(
                        ChallengesImgEntity.builder()
                                .originalFilename( fileDto.getOriginalFilename() )
                                .uuidFile( fileDto.getUuidFile())
                                .build()
                );
                // 단방향 : 이미지객체에 제품객체 등록
                challengesImgEntity.setChallengesEntity(challengesEntity);
                // 양방향 : 제품객체에 이미지객체 등록
                challengesEntity.getChallengesImgEntitiy().add( challengesImgEntity );
            });
        }

        return true;
    }

    @Transactional
    public boolean put(ChallengesDto challengesDto){
        Optional<ChallengesEntity> entityOptional = challengesEntityRepository.findById(challengesDto.getChno());
        if(entityOptional.isPresent()){
            ChallengesEntity entity = entityOptional.get();
            entity.setChname(challengesDto.getChname());
            entity.setChcontent(challengesDto.getChcontent());
        }
        return true;
    }

    @Transactional
    public boolean delete(int chno){
        Optional<ChallengesEntity> entityOptional = challengesEntityRepository.findById(chno);
        entityOptional.ifPresent(o->challengesEntityRepository.delete(o));
        return true;
    }
    
    // 관리자 페이지
    @Transactional
    public List<ChallengesDto> getList(){
        List<ChallengesEntity> challengesEntityList = challengesEntityRepository.findAll();
        List<ChallengesDto> challengesDtoList = new ArrayList<>();
        challengesEntityList.forEach((b)->{
            List<FileDto> challengesImglist = new ArrayList<>();
            b.getChallengesImgEntitiy().forEach((r)->{
                challengesImglist.add( r.toDto() );
            });
            ChallengesDto dto = b.todto();
            dto.setChfiles(challengesImglist);
            challengesDtoList.add(dto);
        });
        log.info("Challenges"+challengesDtoList);
        return challengesDtoList;
    }
    
    //상세보기
    @Transactional
    public ChallengesDto getDetail( int chno ){
        Optional<ChallengesEntity> challengesEntity = challengesEntityRepository.findById(chno);

        if(challengesEntity.isPresent()){
            ChallengesEntity entity = challengesEntity.get();

            List<FileDto> list = new ArrayList<>();
            entity.getChallengesImgEntitiy().forEach((r)->{
                list.add( r.toDto() );
            });
            ChallengesDto dto = entity.todto();
            dto.setChfiles(list);
            log.info("Challenges"+dto);
            return dto;
        }
        return null;
    }

    // 챌린지 참여
    // 상세페이지에서 참여 목록
    @Transactional
    public List<ChallengeResultsDto> getResult(int chno){
        String datenow = LocalDateTime.now().toLocalDate().toString();
        List<ChallengeResultsEntity> challengeResultsEntityList = challengeResultsEntityRepository.findByChno(chno,datenow);
        List<ChallengeResultsDto> challengeResultsDtoList = new ArrayList<>();
        challengeResultsEntityList.forEach(e->{
            ChallengeResultsDto dto = e.toDto();
            dto.setChno(e.getChallengesEntity().getChno());
            dto.setMno(e.getMemberEntity().getMno());
            dto.setMemail(e.getMemberEntity().getMemail());
            challengeResultsDtoList.add(dto);
        });
        log.info("Challenges"+challengeResultsDtoList);
        return challengeResultsDtoList;
    }

    // 로그인한 사람 참여상황
    @Transactional
    public List<ChallengeResultsDto> getResultByMno(ChallengeResultsDto resultsDto){
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMemail(resultsDto.getMemail());
        if(!optionalMemberEntity.isPresent()){return null;}
        MemberEntity memberEntity = optionalMemberEntity.get();

        log.info("getResultByMno : "+resultsDto);
        List<ChallengeResultsEntity> challengeResultsEntityList = challengeResultsEntityRepository.findByMno(resultsDto.getChno(),memberEntity.getMno());
        List<ChallengeResultsDto> challengeResultsDtoList = new ArrayList<>();
        challengeResultsEntityList.forEach(e->{
            ChallengeResultsDto dto = e.toDto();
            dto.setChno(e.getChallengesEntity().getChno());
            dto.setMno(e.getMemberEntity().getMno());
            dto.setMemail(e.getMemberEntity().getMemail());
            challengeResultsDtoList.add(dto);
        });
        log.info("getResultByMno : "+challengeResultsDtoList);
        return challengeResultsDtoList;
    }

    // 관리자 페이지에서 참여 목록
    @Transactional
    public List<ChallengeResultsDto> getResultAdmin(int chno){
        List<ChallengeResultsEntity> challengeResultsEntityList = challengeResultsEntityRepository.findByChnoAndState(chno);
        List<ChallengeResultsDto> challengeResultsDtoList = new ArrayList<>();
        challengeResultsEntityList.forEach(e->{
            ChallengeResultsDto dto = e.toDto();
            dto.setChno(e.getChallengesEntity().getChno());
            dto.setMno(e.getMemberEntity().getMno());
            dto.setMemail(e.getMemberEntity().getMemail());
            challengeResultsDtoList.add(dto);
        });
        log.info("Challenges"+challengeResultsDtoList);
        return challengeResultsDtoList;
    }

    // 오늘의 인증 등록
    @Transactional
    public boolean postResult(ChallengeResultsDto challengeResultsDto){
        log.info("Challenges"+challengeResultsDto);
        // 0. 로그인 했는지 회원정보 호출[ 댓글 작성자  ]
        //Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //if( o.equals("anonymousUser")){ return false; }
        //MemberDto memberDto = (MemberDto)o;
        //MemberEntity memberEntity = memberEntityRepository.findById(  memberDto.getMno() ).get();

        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMemail(challengeResultsDto.getMemail());
        if(!optionalMemberEntity.isPresent()){return false; }
        MemberEntity memberEntity = optionalMemberEntity.get();

        // 0. 댓글작성할 게시물 호출
        Optional<ChallengesEntity> optionalChallengesEntity = challengesEntityRepository.findById(challengeResultsDto.getChno());
        if( !optionalChallengesEntity.isPresent() ){ return false; }
        ChallengesEntity challengesEntity = optionalChallengesEntity.get();

        // 1. 챌린지 작성(이미지 저장)
        if( !challengeResultsDto.getSimg().isEmpty() ){
            // 업로드된 파일 결과[리턴]
            FileDto fileDto = fileService.fileupload( challengeResultsDto.getSimg() );
            // DB 저장
            ChallengeResultsEntity challengeResultsEntity = challengeResultsEntityRepository.save(
                    ChallengeResultsEntity.builder()
                            .originalFilename(fileDto.getOriginalFilename())
                            .uuidFile(fileDto.getUuidFile())
                            .build()
            );
            if( challengeResultsEntity.getSno() < 1 ) { return  false; }

            // 2. 댓글과 회원의 양방향 관계[ 댓글->회원 / 회원 -> 댓글 == 양방향  ,  댓글->회원 == 단방향  ]
            challengeResultsEntity.setMemberEntity(  memberEntity );
            memberEntity.getChallengeResultsEntityList().add( challengeResultsEntity );
            // 3. 댓글과 게시물의 양방향 관계 [ 댓글->게시물 / 게시물->댓글 == 양방향 , 댓글->게시물 == 단방향 ]
            challengeResultsEntity.setChallengesEntity(challengesEntity);
            challengesEntity.getChallengeResultsEntityList().add(challengeResultsEntity);
            return true;
        }
        return false;
    }

    // 관리자 페이지 인증 확인
    @Transactional
    public boolean putResult(ChallengeResultsDto challengeResultsDto){
        log.info("sno : " + challengeResultsDto);
        Optional<ChallengeResultsEntity> optionalChallengeResultsEntity = challengeResultsEntityRepository.findById(challengeResultsDto.getSno());
        if(optionalChallengeResultsEntity.isPresent()){
            ChallengeResultsEntity entity = optionalChallengeResultsEntity.get();
            entity.setSstate(challengeResultsDto.getSstate());
            return true;
        }
        return false;
    }

}
