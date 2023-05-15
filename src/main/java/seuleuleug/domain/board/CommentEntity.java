package seuleuleug.domain.board;

import lombok.*;
import seuleuleug.domain.hospital.HMemberEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // 게시물 fk
    @ManyToOne
    @JoinColumn(name = "bno")
    @ToString.Exclude
    private BoardEntity boardEntity;
    // 작성자 fk
    @ManyToOne
    @JoinColumn(name = "hmno")
    @ToString.Exclude
    private HMemberEntity hMemberEntity;

    public CommentDto toCommentDto() {
        return CommentDto.builder()
                .hmno(this.hMemberEntity.getHmno())
                .hmemail(this.hMemberEntity.getHmemail())
                .hmname(this.hMemberEntity.getHmname())
                .bno(this.boardEntity.getBno())
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rdate(this.rdate)
                .hname(this.hMemberEntity.getHospitalEntity().getHname())
                .build();
    }
}
