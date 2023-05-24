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

    @GetMapping("/info")
    public String info(){
        String result = memberService.info();
        return  result;
    }
    
    // 회원탈퇴
    @DeleteMapping("/delete")
    public boolean delete(){return memberService.delete();}
    
    /* 시큐리티 사용 전
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
        log.info("after logout logintype : " +request.getSession().getAttribute("logintype"));
        log.info("after logout email : " +request.getSession().getAttribute("email"));
        return true;
    }*/
}
