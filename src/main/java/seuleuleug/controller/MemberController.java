package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.service.MemberService;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/singup")
    public boolean signup(@RequestBody MemberDto memberDto){
        return memberService.signup(memberDto);
    }
}
