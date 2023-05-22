package seuleuleug.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    // 1. S3
        // 업로드[바이트를 복사한다는 개념]
        // 1. S3 객체 생성
    @Autowired
    private AmazonS3Client amazonS3Client;
    // *** application.properties의 버킷 설정값 가져와서 변수에 저장[서비스 보완에 관련된 코드 숨기기]
    @Value("${cloud.aws.s3.bucket}") // lombok 아님
    private String bucket; // application.properties에 설정한 버킷명 가져오기
    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl; // application.properties에 설정한 버킷경로 가져오기


    // 3. s3 리소스 삭제 함수 선언
    public void deleteS3(String uuidFile){
        // 1. 삭제 요청 객체 생성
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, uuidFile.replace(defaultUrl,""));
        // 2. 삭제 요청
        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    
        // 2. S3 업로드 함수 선언
    public void uploadS3(String uuidFile, File file){
        // 1. aws s3 전송 객체 생성
        TransferManager transferManager = new TransferManager(amazonS3Client);
        // 2. 요청객체
        // PutObjectRequest putObjectRequest = new PutObjectRequest("버킷명" , 파일명, 업로드할 파일);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket , uuidFile, file);
        // 3. 업로드 객체 생성 [ 대기 ]
        Upload upload = transferManager.upload(putObjectRequest);
        // 4. 업로드 객체 업로드 실행
        try {
            upload.waitForCompletion();
        }catch(InterruptedException e){
                throw new RuntimeException(e);
        }
    }

    // 2. ec2
    //String path = "/home/ec2-user/app/seuleuleug/build/resources/main/static/static/media/";

    // 3. local
    // String path = "C:\\Users\\504\\Desktop\\seuleuleug\\build\\resources\\main\\static\\static\\media\\";
    // String path = "C:\\Users\\kjkim\\IdeaProjects\\seuleuleug\\build\\resources\\main\\static\\static\\media\\";
    public FileDto fileupload(MultipartFile multipartFile ){
        String s3url = null; // 업로드 된 s3리소스가 저장된 경로
        //log.info("File upload : " + multipartFile);
        //log.info("File upload : " + multipartFile.getOriginalFilename() ); // 실제 첨부파일 파일명
        log.info("File upload : " + multipartFile.getName() );              // input name
        //log.info("File upload : " + multipartFile.getContentType() );       // 첨부파일 확장자
        //log.info("File upload : " + multipartFile.getSize() );              // 99 995 바이트
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
                File file = new File( System.getProperty("user.dir") + fileName );
                //System.getProperty("user.dir") 현재 실행되는 컴퓨터 루트 경로

                // 3. 업로드 // multipartFile.transferTo( 저장할 File 클래스의 객체 );
                //FileCopyUtils.copy(bytes, file);
                multipartFile.transferTo(file);
                // *** S3 클라우드에 업로드
                uploadS3(fileName,file);
                // 업로드 된 s3리소스 경로
                s3url = defaultUrl+fileName;
                // 로컬 파일 삭제
                file.delete();

            }catch ( Exception e ) { log.info("file upload fail : " + e ); }
            // 4. 반환
            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename() )
                    .uuidFile( s3url )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();
        }
        return null;
    }

    @Autowired
    private HttpServletResponse response; // 응답 객체

    public void filedownload( String uuidFile ){ // spring 다운로드 관한 API 없음
        //String pathFile = uuidFile; // 경로+uuid파일명 : 실제 파일이 존재하는 위치
        String uuidFileName = uuidFile.replace(defaultUrl,"");
        String realFileName = uuidFileName.split("_")[1];
        try {
            // 1. 다운로드 형식 구성
            response.setHeader(  "Content-Disposition", // 헤더 구성 [ 브라우저 다운로드 형식 ]
                    "attchment;filename = " + URLEncoder.encode( realFileName, "UTF-8") // 다운로드시 표시될 이름
            );

            /*
                // 로컬일 경우
            //2. 다운로드 스트림 구성
            File file = new File( pathFile ); // 다운로드할 파일의 경로에서 파일객체화
            // 3. 입력 스트림 [  서버가 먼저 다운로드 할 파일의 바이트 읽어오기 = 대상 : 클라이언트가 요청한 파일 ]
            BufferedInputStream fin = new BufferedInputStream( new FileInputStream(file) );
            byte[] bytes = new byte[ (int) file.length() ]; // 파일의 길이[용량=바이트단위] 만큼 바이트 배열 선언
            fin.read( bytes ); // 읽어온 바이트들을 bytes배열 저장
            */
                // s3일 경우 [ 버킷에서 해당 리소스 객체를 바이트로 가져오기 ]
            // 1. 불러오길 할 버킷의 리소스 호출
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket,uuidFileName);
            // 2. 요청 객체
            S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
            // 3. s3스트림 객체 생성
            S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
            // 4. s3 스트림을 이용해서 읽어온 바이트를 배열에 저장
            byte[] bytes = IOUtils.toByteArray(objectInputStream);


            // 4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림  = 대상 : response = 현재 서비스 요청한 클라이언트에게 ]
            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() );
            fout.write( bytes ); // 입력스트림에서 읽어온 바이트 배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
            fout.close();
        }catch(Exception e){ log.info("file download fail : "+e );}
    }
}