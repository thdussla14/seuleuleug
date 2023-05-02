package seuleuleug.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class MemberDto {
    private int mno;            //회원번호
    private String memail;       //회원이메일
    private String mphone;       //회원전화번호

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mphone(this.mphone)
                .build();
    }

}
