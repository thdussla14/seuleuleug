package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.service.MemberService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/singup")
    public boolean signup(@RequestBody MemberDto memberDto){
        log.info("signup dto: " + memberDto);
        return memberService.signup(memberDto);
    }

    @GetMapping("/login")
    public MemberDto login(@RequestParam("memail") String memail, @RequestParam("mphone") String mphone ){
        log.info("login memail: " + memail + " mphone: " + mphone);
        MemberDto result = memberService.login(memail, mphone);
        if (result != null) {
            log.info(request.getSession().getAttribute("logintype").toString());
            log.info(request.getSession().getAttribute("email").toString());
        }
        return result;
    }

    @GetMapping("/logout")
    public boolean logout(){
        request.getSession().setAttribute("logintype" , null);
        request.getSession().setAttribute("email" , null);
        return true;
    }
}
