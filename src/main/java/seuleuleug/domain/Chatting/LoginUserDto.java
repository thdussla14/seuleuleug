package seuleuleug.domain.Chatting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class LoginUserDto {
    private WebSocketSession session; // 들어와있는 세션
    private String type;              // 로그인형태 ( 의사, 일반회원, 비로그인)
    private String userEmail;         // 식별을 위한 이메일
}
