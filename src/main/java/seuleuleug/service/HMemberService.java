package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.board.BoardEntity;
import seuleuleug.domain.challenges.FileDto;
import seuleuleug.domain.hospital.*;
import seuleuleug.domain.member.MemberEntity;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class HMemberService {

    @Autowired
    private HospitalEntityRepository hospitalEntityRepository;
    @Autowired
    private HMemberRepository hMemberRepository;
    @Autowired
    private MemberEntityRepository memberEntityRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private HttpServletRequest request;

    // 의사 회원가입
    @Transactional
    public boolean hsignup(HMemberDto hMemberDto){
        Optional<HospitalEntity> optionalHospitalEntity = hospitalEntityRepository.findById(hMemberDto.getHno());
        // 이메일중복체크(의사&회원)
        Optional<MemberEntity> mentityOptional = memberEntityRepository.findByMemail(hMemberDto.getHmemail() );
        Optional<HMemberEntity> hentityOptional = hMemberRepository.findByHmemail(hMemberDto.getHmemail());

        if(optionalHospitalEntity.isPresent()) {
            if(!mentityOptional.isPresent()){ // 이메일중복체크(의사&회원)
                if(!hentityOptional.isPresent()){
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
            }
        }
        return false;
    }
    // 의사 승인 상태 변경
    @Transactional
    public boolean hupdate(HMemberDto hMemberDto){
        log.info("hupdate service: " + hMemberDto);
        Optional<HMemberEntity> entityOptional = hMemberRepository.findById(hMemberDto.getHmno());
        if(entityOptional.isPresent()){
            entityOptional.get().setHmstate(1);
            return true;
        }
        return false;
    }
    // 의사 로그인 시큐리티 사용으로 memberService에서 처리
    /*public HMemberDto hlogin(String hmemail , String hpassword){
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
    }*/
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

    // 의사 아이디 찾기
    @Transactional
    public String findId(HMemberDto dto){
        System.out.println(dto);
        Optional<HMemberEntity> entity = hMemberRepository.findByHmnameAndHmphone(dto.getHmname(), dto.getHmphone());
        if( entity.isPresent() ){
            return entity.get().getHmemail();
        }
        return null;
    }

    // 의사 비밀번호 찾기
    @Transactional
    public String findPwd(HMemberDto dto){
        System.out.println(dto);
        boolean result = hMemberRepository.existsByHmemailAndHmphone(dto.getHmemail(), dto.getHmphone());
        if(result){
            Random random = new Random();
            // 표현할 난수 문자 목록
            String ranStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            // 임시비밀번호 만들기
            String updatePwd = "";
            for( int i = 0 ; i<12 ; i++ ) { // 12자리수
                // ranStr 문자열에서 0인덱스~마지막인덱스 의 난수 인덱스 만들기
                int ran = random.nextInt( ranStr.length() );
                updatePwd += ranStr.charAt( ran );    // 난수로 생성된 인덱스의 문자1개 추출해서 대입
            } // for end
            System.out.println( "updatePwd : " + updatePwd );
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Optional<HMemberEntity> entityOptional = hMemberRepository.findByHmemail( dto.getHmemail() );
            if(entityOptional.isPresent()){
                HMemberEntity entity = entityOptional.get();
                entity.setHpassword( passwordEncoder.encode( updatePwd ));
            }
            return updatePwd;
        }
        return null;
    }
    
    // 회원탈퇴
    @Transactional
    public boolean deleteMember(String hpassword){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 1. 인증된 인증 정보  찾기
        if( o.equals("anonymousUser") ){ return false; }
        HMemberDto loginDto = (HMemberDto) o;   // 2. 형변환
        Optional<HMemberEntity> hMemberEntityOptional = hMemberRepository.findByHmemail( loginDto.getHmemail() );

        //Optional<HMemberEntity> hMemberEntityOptional = hMemberRepository.findByHmemail( hmemail );
        if(hMemberEntityOptional.isPresent()){
            HMemberEntity entity = hMemberEntityOptional.get();
            // 암호화 된 비밀번호와 입력받은 비밀번호 비교
            if( new BCryptPasswordEncoder().matches( hpassword , entity.getHpassword() )){
                hMemberRepository.delete( entity );
                return true;
            }
        }
        return false;
    }
}
