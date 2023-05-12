package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.Session;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
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
        // 저장
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno()>0){
            return true;
        }
        return false;
    }
    // 일반회원 로그인
    @Transactional
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
    }

}
