package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import seuleuleug.domain.challenges.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<ChallengesDto> get(){
        List<ChallengesEntity> challengesEntityList = challengesEntityRepository.findAll();
        List<ChallengesDto> challengesDtoList = challengesEntityList.stream().map(o->o.todto()).collect(Collectors.toList());
        return challengesDtoList;
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
