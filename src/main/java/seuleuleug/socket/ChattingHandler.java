package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component // 빈 등록 [ 스프링 해당 클래스를 관리 = 제어 역전 == IOC]
@Slf4j
public class ChattingHandler extends TextWebSocketHandler {
    private static Map<String, List<WebSocketSession>> sessionsByChatRoom = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String uri = session.getUri().toString();
        String chatRoomId = uri.substring(uri.lastIndexOf('/') + 1);
        log.info("chatRoomId : " + chatRoomId);
        sessionsByChatRoom.computeIfAbsent(chatRoomId, k -> new ArrayList<>()).add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String uri = session.getUri().toString();
        String chatRoomId = uri.substring(uri.lastIndexOf('/') + 1);
        List<WebSocketSession> sessions = sessionsByChatRoom.get(chatRoomId);
        if (sessions != null) {
            for (WebSocketSession sess: sessions) {
                if (sess.isOpen()) {
                    sess. sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatRoomId = (String) session.getAttributes().get("chatRoomId");
        List<WebSocketSession> sessions = sessionsByChatRoom.getOrDefault(chatRoomId, new ArrayList<>());
        sessions.remove(session);
    }

}
