package seuleuleug.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;            //회원번호
    @Column(nullable = false)
    private String memail;       //회원이메일
    @Column(nullable = false)
    private String mphone;       //회원전화번호

    public MemberDto toDto() {
        return MemberDto.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mphone(this.mphone)
                .build();
    }

}
