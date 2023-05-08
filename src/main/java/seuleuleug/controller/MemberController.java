package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        log.info("signup dto: " + memberDto);
        return memberService.signup(memberDto);
    }

    @GetMapping("/login")
    public MemberDto login(@RequestParam("memail") String memail, @RequestParam("mphone") String mphone){
        log.info("login memail: " + memail + " mphone: " + mphone);
        return memberService.login(memail, mphone);
    }
}
