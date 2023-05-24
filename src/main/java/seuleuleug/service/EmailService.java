package seuleuleug.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import seuleuleug.domain.board.EmailMessage;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(EmailMessage emailMessage) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setFrom("panda6209@naver.com");                    //  메일 발신자
            mimeMessageHelper.setTo(emailMessage.getTo());                        // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject());              // 메일 제목
            mimeMessageHelper.setText(emailMessage.getMessage(), true);    // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            log.info("Success!!");
        } catch (Exception e) {
            log.info("fail!!");
            throw new RuntimeException(e);
        }
    }
}
