package seuleuleug.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import seuleuleug.domain.challenges.ChallengeResultsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;            //회원번호
    @Column(nullable = false, unique = true)
    private String memail;       //회원이메일
    @Column
    private String mphone;       //회원전화번호
    @Column
    private String mrole;       // 회원등급

    @OneToMany(mappedBy = "memberEntity" , cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChallengeResultsEntity> challengeResultsEntityList = new ArrayList<>();

    public MemberDto toDto() {
        return MemberDto.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mphone(this.mphone)
                .mrole(this.mrole)
                .build();
    }

}
