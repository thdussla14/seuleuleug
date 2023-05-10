package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.service.MemberService;

import javax.servlet.http.HttpSession;

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
    public MemberDto login(@RequestParam("memail") String memail, @RequestParam("mphone") String mphone, HttpSession session){
        log.info("login session : " + session);
        log.info("login memail: " + memail + " mphone: " + mphone);
        MemberDto result = memberService.login(memail, mphone,session);
        if (result != null) {
            // Store memail in the session
            session.setAttribute("memail", memail);
        }
        return result;
    }
}
