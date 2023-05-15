package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HMemberDto {

    private int hmno;                   // 의사 번호
    private String hmemail;             // 의사 이메일
    private String hpassword;           // 의사 비밀번호
    private String hmname;              // 의사 이름
    private String hmphone;             // 의사 전화번호
    private MultipartFile hmpimg;              // 의사 프로필 img 파일
    private MultipartFile hmcertification;     // 의사 의사증명서 pdf 파일
    private int hmstate;                // 의사 인증( 0 : 미승인 , 1 : 승인 )
    private int hno;                    // 소속 병원 번호


    public HMemberEntity toEntity(){
        return HMemberEntity.builder()
                .hmno(this.hmno)
                .hmemail(this.hmemail)
                .hpassword(this.hpassword)
                .hmname(this.hmname)
                .hmphone(this.hmphone)
                .hmstate(this.hmstate)
                .build();
    }

}
