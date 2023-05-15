package seuleuleug.domain.challenges;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import seuleuleug.domain.BaseTime;
import seuleuleug.domain.member.MemberEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challengesResults")
public class ChallengeResultsEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int sno;
    @ColumnDefault("0") @Column
    private int sstate;
    @Column
    private String uuidFile;
    @Column
    private String originalFilename;

    // 게시물fk
    @ManyToOne@JoinColumn(name = "chno")@ToString.Exclude
    private ChallengesEntity challengesEntity;
    // 작성자fk
    @ManyToOne@JoinColumn(name="mno")@ToString.Exclude
    private MemberEntity memberEntity;

    public ChallengeResultsDto toDto(){
        return ChallengeResultsDto.builder()
                .uuidFile(this.uuidFile)
                .originalFilename(this.originalFilename)
                .sno(this.sno)
                .sstate(this.sstate)
                .cdate(this.cdate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") ))
                .udate(this.udate.toLocalDate().format( DateTimeFormatter.ofPattern( "yy-MM-dd") ))
                .build();
    }

}
