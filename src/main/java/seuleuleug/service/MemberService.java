package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.transaction.Transactional;

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

}
