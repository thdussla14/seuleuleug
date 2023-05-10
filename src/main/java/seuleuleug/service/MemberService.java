package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.Session;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 일반 회원 회원가입
    @Transactional
    public boolean signup(MemberDto memberDto){
        MemberEntity entity = memberEntityRepository.save(memberDto.toEntity());
        if(entity.getMno()>0){
            return true;
        }
        return false;
    }
    public MemberDto login(String memail, String mphone, HttpSession session){
        log.info("login service session : " + session);
        log.info("login service memail: " + memail + " password: " + mphone);
        Optional<MemberEntity> optionalMemberEntity= memberEntityRepository.findByMemailAndMphone(memail, mphone);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();

            return memberEntity.toDto();
        }
        return null;
    }

}
