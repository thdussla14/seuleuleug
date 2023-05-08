package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
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
        String chatRoomId = (String) session.getAttributes().get("chatRoomId");
        log.info("session 111 : " + session);
        log.info("chatRoomId : " + chatRoomId);
        log.info("session 222 : " + session.getAttributes());
        sessionsByChatRoom.forEach( (e,i) ->{
            log.info("session 333 : " + e);
            log.info("session 444 : " + i);
        });
        sessionsByChatRoom.computeIfAbsent(chatRoomId, k -> new ArrayList<>()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String chatRoomId = (String) session.getAttributes().get("chatRoomId");
        List<WebSocketSession> sessions = sessionsByChatRoom.getOrDefault(chatRoomId, new ArrayList<>());
        for (WebSocketSession s : sessions) {
            s.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatRoomId = (String) session.getAttributes().get("chatRoomId");
        List<WebSocketSession> sessions = sessionsByChatRoom.getOrDefault(chatRoomId, new ArrayList<>());
        sessions.remove(session);
    }

}
