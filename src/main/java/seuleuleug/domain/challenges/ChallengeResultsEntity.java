package seuleuleug.domain.challenges;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "challengesResults")
public class ChallengeResultsEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int sno;
    @Column
    private String simg;
    @Column
    private int sstate;



}
