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
import org.springframework.web.bind.annotation.DeleteMapping;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.domain.hospital.HMemberEntity;
import seuleuleug.domain.hospital.HMemberRepository;
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
    private HMemberRepository hMemberRepository;
    @Autowired
    private HttpServletRequest request;

    // 일반 회원 회원가입
    @Transactional
    public boolean signup(MemberDto memberDto){
        // 이메일중복체크(의사&회원)
        Optional<MemberEntity> mentityOptional = memberEntityRepository.findByMemail( memberDto.getMemail() );
        Optional<HMemberEntity> hentityOptional = hMemberRepository.findByHmemail(memberDto.getMemail());

        if(!mentityOptional.isPresent()){
            if(!hentityOptional.isPresent()){
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
            }
        }
        return false;
    }

    // Oauth 유저 회원가입
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 인증[로그인] 결과 토큰 확인
        OAuth2UserService auth2UserService = new DefaultOAuth2UserService();
        // 2. 전달받은 정보 객체
        OAuth2User oAuth2User = auth2UserService.loadUser( userRequest );
        // 3. 클라이언트 id 식별 [ 응답된 JSON 구조 다르기 때문에 클라이언트ID별 (네이버 vs 카카오) 로 처리 ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String email = null;
        if( registrationId.equals("kakao") ) { // 만약에 카카오 회원이면
            Map<String , Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String , Object> profile = (Map<String, Object>) kakao_account.get("profile");
            email = (String) kakao_account.get("email");
        }else if( registrationId.equals("google")) { // 만약에 구글 회원이면
            email = (String) oAuth2User.getAttributes().get("email");
        }

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
            memberDto.setMrole("USER");
            MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
            memberDto.setMno( entity.getMno());
        }else {
            MemberEntity entity = entityOptional.get();
            memberDto = entity.toDto();
        }

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 입력받은 이메일로 멤버 아이디 찾기
        Optional<MemberEntity> mentityOptional = memberEntityRepository.findByMemail(email);
        log.info("loadUserByUs"+email);
        // 입력받은 이메일로 의사 아이디 찾기
        Optional<HMemberEntity> hentityOptional = hMemberRepository.findByHmemail(email);
        if(mentityOptional.isPresent()){
            MemberEntity entity = mentityOptional.get();
            // 유저 확인
            if(entity.getMrole().equals("USER")){
                // 검증후 세션에 저장할 DTO 반환
                MemberDto dto = entity.toDto();
                //권한 목록
                Set<GrantedAuthority> rolelist = new HashSet<>();
                SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+entity.getMrole());
                rolelist.add(role);
                dto.setRoleList( rolelist );
                return dto;
            }
        }
        if(hentityOptional.isPresent()){
            HMemberEntity entity = hentityOptional.get();
            // 의사 확인
            if(entity.getHrole().equals("DOCTOR")){
                // 검증후 세션에 저장할 DTO 반환
                HMemberDto dto = entity.toDto();
                if( dto.getHmstate() == 1 ){
                    //권한 목록
                    Set<GrantedAuthority> rolelist = new HashSet<>();
                    SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+entity.getHrole());
                    rolelist.add(role);
                    dto.setRoleList( rolelist );
                    return dto;
                }
            }
        }
        return null;
    }
    // 세션에 존재하는 회원정보[ 1. 로그인 , 2. 채팅 ]
    @Transactional
    public String info() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( principal.equals("anonymousUser") ){ return null; }
        String username = null;
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_DOCTOR]") ){
            username = ((UserDetails) principal).getUsername();
            return "DOCTOR "+username;
        }else{
            username = ((UserDetails) principal).getUsername();
            return username;
        }
    }

    // 회원탈퇴
    @Transactional
    public boolean delete(){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 1. 인증된 인증 정보  찾기
        if( o.equals("anonymousUser") ){ return false; }
        MemberDto loginDto = (MemberDto) o;   // 2. 형변환
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findByMemail(loginDto.getMemail());

        if(optionalMemberEntity.isPresent()){
            MemberEntity entity = optionalMemberEntity.get();
            memberEntityRepository.delete( entity );
            return true;
        }
        return false;
    }
}
