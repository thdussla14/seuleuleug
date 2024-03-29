package seuleuleug.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.board.BoardDto;
import seuleuleug.domain.board.EmailMessage;
import seuleuleug.service.BoardService;
import seuleuleug.service.EmailService;

@RestController
@RequiredArgsConstructor
public class EmailController {

    @Autowired
    private final EmailService emailService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/send-mail")
    public ResponseEntity sendMail(@RequestBody BoardDto boardDto){

        BoardDto dto = boardService.getBoard(boardDto.getBno());

        String url = "http://ec2-13-209-3-7.ap-northeast-2.compute.amazonaws.com:8080/board/myboard?bno="+boardDto.getBno();


        EmailMessage emailMessage = EmailMessage.builder()
                .from("@naver.com")
                .to(dto.getBemail())
                .subject("[Seuleuleug] 고민글에 답변이 달렸습니다.")
                .message(dto.getBtitle()+"<br/> 고민글에 답변이 달렸습니다. 확인해보세요~ <br/>"+"<a href="+url+"> 클릭 </a>")
                .build();
        emailService.sendMail(emailMessage);
        return new ResponseEntity(HttpStatus.OK);
    }
}
