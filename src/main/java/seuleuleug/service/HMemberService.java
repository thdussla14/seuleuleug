package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.domain.hospital.HMemberEntity;
import seuleuleug.domain.hospital.HMemberRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HMemberService {

    @Autowired
    private HMemberRepository hMemberRepository;

    public boolean hsignup(HMemberDto hMemberDto){
        log.info("qweqwe");

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

    public HMemberDto hlogin(String hmemail , String hpassword){
        log.info("hlogin service: " + hmemail + " / " + hpassword);
        Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findByHmemailAndHpassword(hmemail,hpassword);
        if(optionalHMemberEntity.isPresent()){
            return optionalHMemberEntity.get().toDto();
        }
        return null;
    }

}
