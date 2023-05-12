package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.domain.hospital.HMemberEntity;
import seuleuleug.domain.hospital.HMemberRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HMemberService {

    @Autowired
    private HMemberRepository hMemberRepository;
    @Autowired
    private HttpServletRequest request;

    // 의사 회원가입
    public boolean hsignup(HMemberDto hMemberDto){
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        hMemberDto.setHpassword(passwordEncoder.encode(hMemberDto.getHpassword()));
        // 저장
        HMemberEntity hMemberEntity = hMemberRepository.save(hMemberDto.toEntity());
        if(hMemberEntity.getHmno()>0){
            return true;
        }

        return false;
    }

    public boolean files( MultipartFile multipartFile){

        String path = "src/main/resources/file/";

        log.info("File upload multipartFile : " + multipartFile);
        log.info("File upload Filename : " + multipartFile.getOriginalFilename()); // 실제 첨부파일 파일명
        log.info("File upload Name : " + multipartFile.getName()); //input의 name 값
        log.info("File upload ContentType : " + multipartFile.getContentType()); // 첨부파일 분류/첨부파일 확장자
        log.info("File upload Size : " + multipartFile.getSize()); // 바이트크기

        try {
            String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            File file = new File(path + fileName);
            FileCopyUtils.copy(bytes, file);
            return true;
        } catch (IOException e) {
            log.info("fileUpload Error : " + e);
            return false;
        }
    }

    // 의사 로그인
    public HMemberDto hlogin(String hmemail , String hpassword){
        log.info("hlogin service: " + hmemail + " / " + hpassword);
        // 입력받은 이메일로 아이디 찾기
        HMemberEntity entity = hMemberRepository.findByHmemail(hmemail);
        // 암호화 된 비밀번호와 입력받은 비밀번호 비교
        if( new BCryptPasswordEncoder().matches( hpassword , entity.getHpassword())){
            request.getSession().setAttribute("logintype","doctor");
            request.getSession().setAttribute("email",entity.getHmemail());
            return entity.toDto();
        }
        return null;
    }

}
