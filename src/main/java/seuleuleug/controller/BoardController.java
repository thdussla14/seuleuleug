package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.board.BoardDto;
import seuleuleug.domain.board.CategoryDto;
import seuleuleug.service.BoardService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    // 카테고리 등록 [ 카테고리명 입력 ]
    @PostMapping("/category/write")
    public boolean writeCategory(@RequestBody CategoryDto categoryDto) {
        log.info("cname"+categoryDto);
        return  boardService.writeCategory(categoryDto);
    }

    //  카테고리 출력 [ 반환 : { cno:cname , cno:cname }
    @GetMapping("/category/list")
    public List<CategoryDto> getCategoryList() {
        return boardService.getCategoryList();
    }

    // 글작성 [ 카테고리번호, 제목, 내용 입력 ]
    @PostMapping("/bwrite")
    public byte writeBoard(@RequestBody BoardDto boardDto) {
        log.info("boardDto"+boardDto);
        return   boardService.writeBoard(boardDto);
    }

}
