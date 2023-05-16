package seuleuleug.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class MemberDto implements UserDetails , OAuth2User {
    private int mno;            //회원번호
    private String memail;       //회원이메일
    private String mphone;       //회원전화번호
    private String mrole;       // 회원등급
    private Set<GrantedAuthority> roleList; //권한 목록
    private Map<String , Object> social; // oauth2 인증 회원정보

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mphone(this.mphone)
                .mrole(this.mrole)
                .build();
    }

    // 시큐리티 UserDetails
    @Override // 인증된 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleList;
    }
    @Override // 패스워드 반환
    public String getPassword() {
        return this.mphone;
    }
    @Override // 계정 반환
    public String getUsername() {
        return this.memail;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User
    @Override // Oauth2 회원정보
    public Map<String, Object> getAttributes() { return this.social; }
    @Override // Oauth2 아이디
    public String getName() { return this.memail; }
}
