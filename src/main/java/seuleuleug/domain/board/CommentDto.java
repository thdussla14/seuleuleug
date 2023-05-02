package seuleuleug.domain.board;

import lombok.*;

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

    private String  mno;        //


    public  CommentEntity toCommentEntity() {
        return CommentEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(this.rdate)
                .build();
    }
}
