package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.Session;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User> {
    @Autowired
    private MemberEntityRepository memberEntityRepository;
    @Autowired
    private HttpServletRequest request;

    // 일반 회원 회원가입
    @Transactional
    public boolean signup(MemberDto memberDto){
        // 비밀번호(=mphone) 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setMphone(passwordEncoder.encode(memberDto.getMphone()));
        // 등급 부여
        memberDto.setMrole("USER");
        // 저장
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno()>0){
            return true;
        }
        return false;
    }
    // Oauth 유저 회원가입
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService auth2UserService = new DefaultOAuth2UserService(); log.info("서비스 정보 : "+auth2UserService.loadUser( userRequest ));
        // 2. 전달받은 정보 객체
        OAuth2User oAuth2User = auth2UserService.loadUser( userRequest ); log.info("회원정보 : "+ oAuth2User.getAttributes());
        // 3. 카카오 정보 호출
        Map<String , Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
        Map<String , Object> profile = (Map<String, Object>) kakao_account.get("profile");
        String email = (String) kakao_account.get("email");
        log.info("회원정보 : "+ email );
        // 인가 객체 [ OAuth2User ---> MemberDto 통합Dto ( 일반+aouth ) ]
        MemberDto memberDto = new MemberDto();
        memberDto.setSocial( oAuth2User.getAttributes());
        memberDto.setMemail(email);
            Set<GrantedAuthority> roleList = new HashSet<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_USER");
            roleList.add(role);
        memberDto.setRoleList(roleList);
        // 1. DB 저장하기 전에 해당 이메일로 된 이메일 존재하는지 검사( DB중복 저장 방지 )
        Optional<MemberEntity> entityOptional = memberEntityRepository.findByMemail(email);
        if(!entityOptional.isPresent()){
            log.info("확인2 : "+ entityOptional );
            memberDto.setMrole("USER");
            MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
            memberDto.setMno( entity.getMno());
        }else {
            MemberEntity entity = entityOptional.get();
            memberDto = entity.toDto();
        }
        log.info("확인3 : "+ memberDto );
        return memberDto;
    }
    // 일반회원 로그인 시큐리티 전
    /*@Transactional
    public MemberDto login(String memail, String mphone){
        log.info("login service memail: " + memail + " password: " + mphone);
        // 입력받은 이메일로 아이디 찾기
        MemberEntity entity = memberEntityRepository.findByMemail(memail);
        // 암호화 된 전화번호와 입력받은 전화번호 비교
        if( new BCryptPasswordEncoder().matches( mphone , entity.getMphone())){
            request.getSession().setAttribute("logintype","normal");
            request.getSession().setAttribute("email", entity.getMemail());
            return entity.toDto();
        }
        return null;
    }*/
    // 스프링 시큐리티 적용했을때 사용되는 로그인 메소드
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 입력받은 이메일로 아이디 찾기
        Optional<MemberEntity> entityOptional = memberEntityRepository.findByMemail(memail);
        if(entityOptional.isPresent()){
            MemberEntity entity = entityOptional.get();

            if( entity == null ){ return null; }
            // 검증후 세션에 저장할 DTO 반환
            MemberDto dto = entity.toDto();
            //권한 목록
            Set<GrantedAuthority> rolelist = new HashSet<>();
            SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+entity.getMrole());
            rolelist.add(role);
            dto.setRoleList( rolelist );
            return dto;
        }
        return null;
    }
    // 세션에 존재하는 회원정보[ 1. 로그인 , 2. 채팅 ]
    @Transactional
    public MemberDto info() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보 호출
        if( o.equals("anonymousUser") ){ return null; }
        log.info((String) o);
        return (MemberDto) o;
    }

}
