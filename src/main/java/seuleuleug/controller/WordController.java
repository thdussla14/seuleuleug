package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.board.CategoryDto;
import seuleuleug.domain.fortune.WordDto;
import seuleuleug.service.BoardService;
import seuleuleug.service.WordService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    // 응원글귀 등록
    @PostMapping("")
    public boolean writeWord(@RequestBody WordDto wordDto) {
        log.info("wcontent"+wordDto);
        return  wordService.writeWord(wordDto);
    }

    //  응원글귀 출력
    @GetMapping("")
    public WordDto getWord() {
        return wordService.getWord();
    }

    //  모든 응원글귀 출력
    @GetMapping("/all")
    public List<WordDto> getWords() {
        return wordService.getWords();
    }
    // 응원글귀 삭제
    @DeleteMapping("")
    public boolean ondelete(@RequestParam int wno){ return wordService.ondelete(wno);}

}
