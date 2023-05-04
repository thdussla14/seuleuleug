package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.domain.hospital.HMemberEntity;
import seuleuleug.domain.hospital.HMemberRepository;

import java.io.File;
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

        String path = "src/main/resources/file";

        log.info("File upload multipartFile : " + multipartFile);
        log.info("File upload Filename : " + multipartFile.getOriginalFilename()); // 실제 첨부파일 파일명
        log.info("File upload Name : " + multipartFile.getName()); //input의 name 값
        log.info("File upload ContentType : " + multipartFile.getContentType()); // 첨부파일 분류/첨부파일 확장자
        log.info("File upload Size : " + multipartFile.getSize()); // 바이트크기

        if(!multipartFile.getOriginalFilename().equals("")){ // 첨부파일이 존재하면
            //*만약에 다른이미지인데 파일명이 동일하면 중복발생 [ 식별 불가 ]
            String fileName = UUID.randomUUID().toString()+"_"+multipartFile.getOriginalFilename();

            // 2. 경로 + 파일명 조합해서 file 클래스 생성
            File file = new File(path+fileName);
            // 3. 업로드 : multipartFile.transferTo(저장할 File 클래스의 객체);
            try {
                multipartFile.transferTo(file);
            }catch (Exception e) { log.info("fileUpload Error : " + e);}
            // 4. 반환
            return true;
        }
        return false;
    }

}
