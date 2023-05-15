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
        if(optionalHospitalEntity.isPresent()) {
            // 비밀번호 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hMemberDto.setHpassword(passwordEncoder.encode(hMemberDto.getHpassword()));
            // 등급부여
            hMemberDto.setHrole("DOCTOR");
            // 파일 저장
            FileDto fileDto1 = fileService.fileupload(hMemberDto.getDoctorpimg());
            FileDto fileDto2 = fileService.fileupload(hMemberDto.getDoctorcertification());
            log.info("fileDto1 : " + fileDto1 + "/" + "fileDto2 : " + fileDto2);
            hMemberDto.setHmpimg(fileDto1.getUuidFile());
            hMemberDto.setHmcertification(fileDto2.getUuidFile());
            // DB 저장
            HMemberEntity hMemberEntity = hMemberRepository.save(hMemberDto.toEntity());
            log.info("hMemberEntity : " + hMemberEntity);
            // 소속 병원 정보
            HospitalEntity hospitalEntity = optionalHospitalEntity.get();
            log.info("HospitalEntity : " + hospitalEntity);
            hMemberEntity.setHospitalEntity(hospitalEntity);
            log.info("hMemberEntity : " + hMemberEntity);
            hospitalEntity.getHMemberEntities().add(hMemberEntity);
            log.info("HospitalEntity : " + hospitalEntity);
            return true;
        }
        return false;
    }
    // 의사 승인 상태 변경
    public boolean hupdate(int hmno){
        log.info("hupdate service: " + hmno);
        Optional<HMemberEntity> entityOptional = hMemberRepository.findById(hmno);
        if(entityOptional.isPresent()){
            entityOptional.get().setHmstate(1);
            return true;
        }
        return false;
    }
    // 의사 로그인
    public HMemberDto hlogin(String hmemail , String hpassword){
        log.info("hlogin service: " + hmemail + " / " + hpassword);
        // 입력받은 이메일로 아이디 찾기
        Optional<HMemberEntity> entityOptional = hMemberRepository.findByHmemail(hmemail);
        if(entityOptional.isPresent()){
            HMemberEntity entity = entityOptional.get();
            // 암호화 된 비밀번호와 입력받은 비밀번호 비교
            if( new BCryptPasswordEncoder().matches( hpassword , entity.getHpassword())){
                request.getSession().setAttribute("logintype","doctor");
                request.getSession().setAttribute("email",entity.getHmemail());
                return entity.toDto();
            }
        }
        return null;
    }
    // 의사 정보 호출
    public HMemberDto get(String hmemail) {
        log.info("get service: " + hmemail);
        Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findByHmemail(hmemail);
        if (optionalHMemberEntity.isPresent()) {
            HMemberDto hMemberDto = optionalHMemberEntity.get().toCommentDto();
            log.info("get hMemberDto: " + hMemberDto);
            return hMemberDto;
        }
        return null;
    }

    // 의사 정보 삭제
    public boolean delete(int hmno){
        log.info("delete service: " + hmno );
        Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findById(hmno);
        if(optionalHMemberEntity.isPresent()) {
            hMemberRepository.delete(optionalHMemberEntity.get());
        }
        return false;
    }

}
