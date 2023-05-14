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

import javax.transaction.Transactional;
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

    @Transactional
    public List<ChallengesDto> getList(){
        List<ChallengesEntity> challengesEntityList = challengesEntityRepository.findAll();
        List<ChallengesDto> challengesDtoList = new ArrayList<>();
        challengesEntityList.forEach((b)->{
            List<FileDto> list = new ArrayList<>();
            b.getChallengesImgEntitiy().forEach((r)->{
                list.add( r.toDto() );
            });
            ChallengesDto dto = b.todto();
            dto.setChfiles(list);
            challengesDtoList.add(dto);
        });
        log.info("Challenges"+challengesDtoList);
        return challengesDtoList;
    }

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
    @Transactional
    public List<ChallengeResultsEntity> getResult(){
        List<ChallengeResultsEntity> challengeResultsEntityList = challengeResultsEntityRepository.findAll();


        return null;
    }

    @Transactional
    public boolean postResult(ChallengeResultsDto challengeResultsDto){
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
        }
        return true;
    }

    @Transactional
    public boolean putResult(int sno){

        return false;
    }

}
