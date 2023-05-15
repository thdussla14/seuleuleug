package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.challenges.FileDto;
import seuleuleug.domain.hospital.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HMemberService {

    @Autowired
    private HospitalEntityRepository hospitalEntityRepository;
    @Autowired
    private HMemberRepository hMemberRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private HttpServletRequest request;

    // 의사 회원가입
    public boolean hsignup(HMemberDto hMemberDto){
        Optional<HospitalEntity> optionalHospitalEntity = hospitalEntityRepository.findById(hMemberDto.getHno());
        if(optionalHospitalEntity.isPresent()){
            FileDto fileDto1 = fileService.fileupload( hMemberDto.getHmpimg());
            FileDto fileDto2 = fileService.fileupload( hMemberDto.getHmcertification());
            log.info("fileDto1 : " + fileDto1+"/"+"fileDto2 : " + fileDto2);
            HMemberEntity hMemberEntity = hMemberRepository.save(hMemberDto.toEntity());
            log.info("HospitalEntity : " + optionalHospitalEntity.get());
            hMemberEntity.setHospitalEntity(optionalHospitalEntity.get());
            log.info("hMemberEntity : " + hMemberEntity);
            log.info("hMemberEntit.getHospitalEntity : "+hMemberEntity.getHospitalEntity());
            optionalHospitalEntity.get().getHMemberEntities().add(hMemberEntity);
            hMemberEntity.setHmpimg(fileDto1.getUuidFile());
            hMemberEntity.setHmcertification(fileDto2.getUuidFile());
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        hMemberDto.setHpassword(passwordEncoder.encode(hMemberDto.getHpassword()));
        // 등급부여
        hMemberDto.setHrole("DOCTOR");
        // 저장
        HMemberEntity hMemberEntity = hMemberRepository.save(hMemberDto.toEntity());
        if(hMemberEntity.getHmno()>0){
            return true;
        }

        return false;
    }

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

    public HMemberDto get(String hmemail){
        log.info("get service: " + hmemail );
        Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findByHmemail(hmemail);
        if(optionalHMemberEntity.isPresent()){
            return optionalHMemberEntity.get().toDto();
        }
        return null;
    }

}
