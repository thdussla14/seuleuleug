package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
            return true;
        }
        return false;
    }

    public HMemberDto hlogin(String hmemail , String hpassword){
        log.info("hlogin service: " + hmemail + " / " + hpassword);
        Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findByHmemailAndHpassword(hmemail,hpassword);
        if(optionalHMemberEntity.isPresent()){
            request.getSession().setAttribute("logintype","doctor");
            request.getSession().setAttribute("email",optionalHMemberEntity.get().getHmemail());
            return optionalHMemberEntity.get().toDto();
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
