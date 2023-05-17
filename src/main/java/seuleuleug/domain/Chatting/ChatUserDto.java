package seuleuleug.domain.Chatting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;


@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ChatUserDto {
    @JsonIgnore
    private String chatRoomId;          //채팅방번호
    @JsonIgnore
    private String type;                // 의사인지 상담받는 사람인지 확인
    @JsonIgnore
    private WebSocketSession session;   // 세션
}
