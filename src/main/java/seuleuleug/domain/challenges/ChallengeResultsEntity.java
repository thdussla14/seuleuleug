package seuleuleug.domain.challenges;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import seuleuleug.domain.BaseTime;
import seuleuleug.domain.member.MemberEntity;

import javax.persistence.*;

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
    @ManyToOne@JoinColumn(name = "bno")@ToString.Exclude
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
                .build();
    }

}
