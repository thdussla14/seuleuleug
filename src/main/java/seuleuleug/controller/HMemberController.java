package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.service.HMemberService;

import java.io.File;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/hmember")
public class HMemberController {
    @Autowired
    private HMemberService hMemberService;

    @PostMapping("/hsignup")
    public boolean hsignup(@RequestBody HMemberDto hMemberDto){
        log.info("hsignup : " + hMemberDto);
        boolean result = hMemberService.hsignup(hMemberDto);
        log.info("result : " + result);
        return result;
    }

    @PostMapping("/files")
    public boolean files(@RequestParam("hmcertification") MultipartFile multipartFile){
        return hMemberService.files(multipartFile);
    }

    @GetMapping("/hlogin")
    public HMemberDto hlogin(@RequestParam("hmemail") String hmemail , @RequestParam("hpassword") String hpassword){
        log.info("hlogin : " + hmemail + " / " + hpassword);
        return hMemberService.hlogin(hmemail , hpassword);
    }



}
