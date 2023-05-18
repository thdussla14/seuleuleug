package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.service.HMemberService;

import javax.transaction.Transactional;
import java.io.File;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/hmember")
public class HMemberController {
    @Autowired
    private HMemberService hMemberService;
    // 의사 회원가입
    @PostMapping("/hsignup")
    public boolean hsignup(HMemberDto hMemberDto){
        log.info("hsignup : " + hMemberDto);
        boolean result = hMemberService.hsignup(hMemberDto);
        log.info("result : " + result);
        return result;
    }
    // 의사 승인

    @PutMapping("/hupdate")
    public boolean hupdate(@RequestBody HMemberDto hMemberDto){
        return hMemberService.hupdate(hMemberDto);
    }
    // 의사 로그인
    /*@GetMapping("/hlogin")
    public HMemberDto hlogin(@RequestParam("hmemail") String hmemail , @RequestParam("hpassword") String hpassword){
        log.info("hlogin : " + hmemail + " / " + hpassword);
        return hMemberService.hlogin(hmemail , hpassword);
    }*/
    // 의사 정보 호출
    @GetMapping("/hcomment")
    public HMemberDto get(@RequestParam("hmemail") String hmemail) {
        log.info("hmemail : " + hmemail );
        return hMemberService.get(hmemail);
    }
    // 의사 정보 삭제
    @DeleteMapping("")
    public boolean delete(@RequestParam("hmno") int hmno){
        log.info("delete : " + hmno);
        return hMemberService.delete(hmno);
    }
}
