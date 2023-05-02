package seuleuleug.domain;

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
    private int     rno ;       //댓글 번호
    @Column(columnDefinition = "longtext") // columnDefinition = "sql자료형"
    private String  rcontent  ; //댓글 내용
    @Column
    private String  rdate ;     //댓글 작성시간


    public CommentDto toCommentDto() {
        return CommentDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(this.rdate)
                .build();
    }
}
