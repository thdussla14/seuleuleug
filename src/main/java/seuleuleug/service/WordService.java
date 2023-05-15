package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import seuleuleug.domain.fortune.WordDto;
import seuleuleug.domain.fortune.WordEntity;
import seuleuleug.domain.fortune.WordEntityRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class WordService {
    @Autowired
    WordEntityRepository wordEntityRepository;

    // 글귀 등록
    public boolean writeWord(WordDto wordDto){
        log.info("writeCategory"+wordDto);
        wordEntityRepository.save(wordDto.toWordEntity());
        return false;
    }

    // 랜덤 글귀 출력
    public WordDto getWord() {
        List<WordEntity> entityList = wordEntityRepository.findAll();
        // 난수 생성
        Random random = new Random();
        Optional<WordEntity> WordEntity = wordEntityRepository.findById(random.nextInt(entityList.size()-1));
        if(WordEntity.isPresent()){
            return WordEntity.get().toWordDto();
        }
        return null;
    }

    //  모든 응원글귀 출력
    public List<WordDto> getWords() {
        List<WordEntity> entityList = wordEntityRepository.findAll();
        List<WordDto> wordDtoList = new ArrayList<>();
        entityList.forEach((w)->{
            wordDtoList.add(w.toWordDto());
        });
        return wordDtoList;
    }

    // 응원글귀 삭제
    public boolean ondelete(int wno) {
        Optional<WordEntity> optionalProductEntity = wordEntityRepository.findById(wno);
        if(optionalProductEntity.isPresent()){
            wordEntityRepository.delete(optionalProductEntity.get());
            return true;
        }
        return false;
    }


}
