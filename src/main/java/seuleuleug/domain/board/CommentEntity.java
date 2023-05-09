package seuleuleug.domain.board;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int     rno ;       //답글 번호
    @Column(columnDefinition = "longtext") // columnDefinition = "sql자료형"
    private String  rcontent  ; //답글 내용
    @Column
    private String  rdate ;     //답글 작성시간
    @Column
    private int     bno;       // 게시물 번호
    @Column
    private int     mno;       // 답글 작성자
    public CommentDto toCommentDto() {
        return CommentDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(this.rdate)
                .bno(this.bno)
                .mno(this.mno)
                .build();
    }
}
