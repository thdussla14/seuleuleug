package seuleuleug.domain.board;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private int     rno ;       //댓글 번호
    private String  rcontent  ; //댓글 내용
    private String  rdate ;     //댓글 작성시간

    private int     bno;
    private int     hmno;
    private String  hmname;
    private String  hmemail;

    public  CommentEntity toCommentEntity() {
        return CommentEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(LocalDate.now().toString())
                .build();
    }

}
