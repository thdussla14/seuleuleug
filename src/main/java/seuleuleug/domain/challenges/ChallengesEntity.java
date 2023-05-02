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
@Table(name = "challenges")
public class ChallengesEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int chno;
    @Column
    private String chname;
    @Column
    private String chcontent;
    @Column
    private String chimg;

    // toDto 출력용
    public ChallengesDto todto(){
        return ChallengesDto.builder()
            .chno(this.chno)
            .chname(this.chname)
            .chcontent(this.chcontent)
            .chimg(this.chimg)
            .build();
    }

}
