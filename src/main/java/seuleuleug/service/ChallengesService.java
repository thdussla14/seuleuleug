package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
}
