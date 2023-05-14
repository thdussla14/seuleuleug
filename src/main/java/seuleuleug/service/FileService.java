package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import seuleuleug.domain.challenges.FileDto;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // * 첨부파일이 저장 될 경로 [ 1. 배포 전 2.배포 후 ]
    //String path = "C:\\Users\\504\\Desktop\\seuleuleug\\build\\resources\\main\\static\\static\\media\\";
    String path = "C:\\Users\\kjkim\\IdeaProjects\\seuleuleug\\build\\resources\\main\\static\\static\\media\\";
    public FileDto fileupload(MultipartFile multipartFile ){
        log.info("File upload : " + multipartFile);
        log.info("File upload : " + multipartFile.getOriginalFilename() ); // 실제 첨부파일 파일명
        log.info("File upload : " + multipartFile.getName() );              // input name
        log.info("File upload : " + multipartFile.getContentType() );       // 첨부파일 확장자
        log.info("File upload : " + multipartFile.getSize() );              // 99 995 바이트
        // 1. 첨부파일 존재하는지 확인
        if( !multipartFile.getOriginalFilename().equals("") ){ // 첨부파일이 존재하면
            String fileName = "";
            try {
                // * 만약에 다른 이미지인데 파일이 동일하면 중복발생[ 식별 불가 ] : UUID + 파일명
            fileName =
                    UUID.randomUUID().toString() +"_"+
                            multipartFile.getOriginalFilename().replaceAll("_","-");
            byte[] bytes = multipartFile.getBytes();
            // 2.  경로 + UUID파일명  조합해서 file 클래스의 객체 생성 [ 왜?? 파일?? transferTo ]
            File file = new File( path + fileName );

            // 3. 업로드 // multipartFile.transferTo( 저장할 File 클래스의 객체 );
            //FileCopyUtils.copy(bytes, file);
            multipartFile.transferTo(file);
            }catch ( Exception e ) { log.info("file upload fail : " + e ); }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( fileName )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();
        }
        return null;
    }

    @Autowired
    private HttpServletResponse response; // 응답 객체

    public void filedownload( String uuidFile ){ // spring 다운로드 관한 API 없음
        String pathFile = path + uuidFile; // 경로+uuid파일명 : 실제 파일이 존재하는 위치
        try {
            // 1. 다운로드 형식 구성
            response.setHeader(  "Content-Disposition", // 헤더 구성 [ 브라우저 다운로드 형식 ]
                    "attchment;filename = " + URLEncoder.encode( uuidFile.split("_")[1], "UTF-8") // 다운로드시 표시될 이름
            );
            //2. 다운로드 스트림 구성
            File file = new File( pathFile ); // 다운로드할 파일의 경로에서 파일객체화
            // 3. 입력 스트림 [  서버가 먼저 다운로드 할 파일의 바이트 읽어오기 = 대상 : 클라이언트가 요청한 파일 ]
            BufferedInputStream fin = new BufferedInputStream( new FileInputStream(file) );
            byte[] bytes = new byte[ (int) file.length() ]; // 파일의 길이[용량=바이트단위] 만큼 바이트 배열 선언
            fin.read( bytes ); // 읽어온 바이트들을 bytes배열 저장
            // 4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림  = 대상 : response = 현재 서비스 요청한 클라이언트에게 ]
            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() );
            fout.write( bytes ); // 입력스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
            fin.close(); fout.close(); // 스트림 닫기
        }catch(Exception e){ log.info("file download fail : "+e );}
    }
}