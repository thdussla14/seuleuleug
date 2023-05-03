package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seuleuleug.domain.fortune.WordDto;
import seuleuleug.domain.fortune.WordEntity;
import seuleuleug.domain.fortune.WordEntityRepository;

import java.util.ArrayList;
import java.util.List;
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
        Random random = new Random();


        return null;
    }
}
