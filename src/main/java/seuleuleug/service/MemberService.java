package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seuleuleug.domain.member.MemberEntityRepository;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberEntityRepository memberEntityRepository;
}
