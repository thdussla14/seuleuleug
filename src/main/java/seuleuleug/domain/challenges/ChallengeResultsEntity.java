package seuleuleug.domain.challenges;

import lombok.*;
import seuleuleug.domain.BaseTime;

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
    @Column
    private String simg;
    @Column
    private int sstate;



}
