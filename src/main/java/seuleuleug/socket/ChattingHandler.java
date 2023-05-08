package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component // 빈 등록 [ 스프링 해당 클래스를 관리 = 제어 역전 == IOC]
@Slf4j
public class ChattingHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> onList = new ArrayList<>();

    @Override // 클라이언트가 접속했을때
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished : " + session);
        onList.add(session); // 세션이 들어왔을때 리스트에 저장
        // 저장하는 이유 : 다른 세션들과의 통신을 위해서
    }

    @Override // 클라이언트로부터 메세지를 받았을때
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage session : " + session);
        log.info("handleTextMessage message : " + message);

        for(WebSocketSession 세션 : onList){
            세션.sendMessage(message);
        }

    }

    @Override // 클라이언트가 접속을 해제했을때
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed session : " + session);
        log.info("afterConnectionClosed status : " + status);
        onList.remove(session); // 세션이 나갔을때 리스트에서 제거
    }
}
