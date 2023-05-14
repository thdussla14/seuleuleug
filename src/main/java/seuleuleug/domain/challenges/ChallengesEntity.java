package seuleuleug.domain.challenges;

import lombok.*;
import seuleuleug.domain.BaseTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challenges")
public class ChallengesEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int chno;
    @Column
    private String chname;
    @Column
    private String chcontent;

    @OneToMany(mappedBy = "challengesEntity" , cascade = CascadeType.REMOVE)
    @ToString.Exclude @Builder.Default
    private List<ChallengesImgEntity> challengesImgEntitiy = new ArrayList<>();

    // pk = 양방향 [ 해당 댓글 엔티티의 fk 필드와 매핑  ]
    // 결과목록
    @OneToMany(mappedBy = "challengesEntity")
    @Builder.Default
    private List<ChallengeResultsEntity> challengeResultsEntityList = new ArrayList<>();

    // toDto 출력용
    public ChallengesDto todto(){
        return ChallengesDto.builder()
            .chno(this.chno)
            .chname(this.chname)
            .chcontent(this.chcontent)
            .cdate(this.cdate.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString() ) ?
                    this.cdate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                    this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") ))
            .udate(this.udate.toLocalDate().toString().equals(LocalDateTime.now().toLocalDate().toString() ) ?
                    this.udate.toLocalTime().format( DateTimeFormatter.ofPattern( "HH:mm:ss") ) :
                    this.udate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") ))
            .build();
    }

}
