package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import seuleuleug.domain.challenges.ChallengesDto;
import seuleuleug.domain.challenges.ChallengesEntity;
import seuleuleug.domain.challenges.ChallengesEntityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChallengesService {
    @Autowired
    ChallengesEntityRepository challengesEntityRepository;

    @Transactional
    public List<ChallengesDto> get(){
        List<ChallengesEntity> challengesEntityList = challengesEntityRepository.findAll();
        List<ChallengesDto> challengesDtoList = challengesEntityList.stream().map(o->o.todto()).collect(Collectors.toList());
        return challengesDtoList;
    }

    @Transactional
    public boolean post(ChallengesDto challengesDto){
        challengesEntityRepository.save(challengesDto.toEntity());
        return true;
    }

    @Transactional
    public boolean put(ChallengesDto challengesDto){
        Optional<ChallengesEntity> entityOptional = challengesEntityRepository.findById(challengesDto.getChno());
        if(entityOptional.isPresent()){
            ChallengesEntity entity = entityOptional.get();
            entity.setChname(challengesDto.getChname());
            entity.setChcontent(challengesDto.getChcontent());
            entity.setChimg(challengesDto.getChimg());
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
