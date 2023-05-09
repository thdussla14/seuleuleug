package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.board.BoardDto;
import seuleuleug.domain.board.CategoryDto;
import seuleuleug.domain.board.CommentDto;
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
    // 이메일 확인
    @GetMapping("/check")
    public boolean checkemail(@RequestParam String bemail) {
        log.info("check boardDto"+bemail);
        return  boardService.checkemail(bemail);
    }
    // 내가 쓴 게시물 목록 출력
    @GetMapping("/mylist")
    public List<BoardDto> getMyBoardList(@RequestParam String bemail){
        log.info("mylist boardDto"+ bemail);
        return  boardService.getMyBoardList(bemail);
    }
    // 전체 게시물 목록 출력
    @GetMapping("/alllist")
    public List<BoardDto> getBoardList(){
        log.info("alllist");
        return  boardService.getBoardList();
    }
    // 게시물 상세 출력
    @GetMapping("/detail")
    public BoardDto getBoard(@RequestParam int bno){
        log.info("detail"+ bno);
        return boardService.getBoard(bno);
    }
    // 게시물 답변 출력
    @GetMapping("/getcomment")
    public List<CommentDto> getCommentList(@RequestParam int bno){
        return boardService.getCommentList(bno);
    }
    // 답글 작성
    @PostMapping("/cwrite")
    public boolean writecomment(@RequestBody CommentDto commentDto){
        return boardService.writecomment(commentDto);
    }
}
