package seuleuleug.domain.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int         bno;            //게시물 번호
    @Column
    private String      btitle;         //게시물 제목
    @Column(columnDefinition = "longtext") // columnDefinition = "sql자료형"
    private String      bcontent;       //게시물 내용
    @Column
    private String      bemail;         //작성자 이메일   => 내가 적은 게시물 목록 호출시 사용 , 답글 작성시 알람 메일로 전달?
    @Column
    private String      bpassword;      //게시물 비밀번호   => 게시물 열람시 필요
    @Column
    private String      blegion ;       //작성자 지역     => 필수?
    @Column
    public  LocalDateTime bdate;        //게시물 작성시간

    // 카테고리 번호
    @ManyToOne // 다수 -> 하나 [ fk => pk ]
    @JoinColumn(name = "cno") // fk 이름 => pk 필드명과 동일
    @ToString.Exclude //해당 필드는  @ToString 제외 필드 [ 단방향은 선택 ]
    private CategoryEntity categoryEntity;

    public BoardDto toBoardDto() {
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .cno(this.categoryEntity.getCno())
                .cname(this.categoryEntity.getCname())
                .build();
    }
}
