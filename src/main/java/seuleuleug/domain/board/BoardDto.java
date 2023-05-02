package seuleuleug.domain.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor@Builder
public class BoardDto {

    private int         bno;            //게시물 번호
    private String      btitle;         //게시물 제목
    private String      bcontent;       //게시물 내용
    private String      bemail;         //작성자 이메일   => 내가 적은 게시물 목록 호출시 사용 , 답글 작성시 알람 메일로 전달?
    private String      bpassword;      //게시물 비밀번호   => 게시물 열람시 필요
    private String      blegion ;       //작성자 지역     => 필수?
    public  LocalDateTime bdate;        //게시물 작성시간
    public  BoardEntity toBoardEntity() {
        return BoardEntity.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bemail(this.bemail)
                .bpassword(this.bpassword)
                .blegion(this.blegion)
                .bdate(this.bdate)
                .build();


    }



}
