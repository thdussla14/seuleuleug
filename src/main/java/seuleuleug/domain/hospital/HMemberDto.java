package seuleuleug.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HMemberDto implements UserDetails {

    private int hmno;                   // 의사 번호
    private String hmemail;             // 의사 이메일
    private String hpassword;           // 의사 비밀번호
    private String hmname;              // 의사 이름
    private String hmphone;             // 의사 전화번호
    private String hmpimg;              // 의사 프로필 img 파일
    private String hmcertification;     // 의사 의사증명서 pdf 파일
    private String hrole;               // 의사 등급
    private int hmstate;                // 의사 인증( 0 : 미승인 , 1 : 승인 )
    private int hno;                    // 소속 병원 번호

    private MultipartFile doctorpimg;
    private MultipartFile doctorcertification;

    private String hname; //소속병원 이름

    private Set<GrantedAuthority> roleList; //권한 목록

    public HMemberEntity toEntity(){
        return HMemberEntity.builder()
                .hmno(this.hmno)
                .hmemail(this.hmemail)
                .hpassword(this.hpassword)
                .hmname(this.hmname)
                .hmphone(this.hmphone)
                .hmstate(this.hmstate)
                .hmpimg(this.hmpimg)
                .hmcertification(this.hmcertification)
                .hrole(this.hrole)
                .build();
    }

    // 시큐리티 UserDetails
    @Override // 인증된 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.roleList; }
    @Override // 패스워드 반환
    public String getPassword() { return this.hpassword; }
    @Override
    public String getUsername() { return this.hmemail; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
