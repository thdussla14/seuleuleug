package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import seuleuleug.domain.Chatting.ChatUserDto;

import java.util.ArrayList;
import java.util.List;

@Component // 빈 등록 [ 스프링 해당 클래스를 관리 = 제어 역전 == IOC]
@Slf4j
public class ChattingHandler extends TextWebSocketHandler {
    protected static List<ChatUserDto> chatUserDtoList = new ArrayList<ChatUserDto>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("서버에 접속");
        log.info("session1111: " + session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info("message2 : " + message.getPayload());
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type");
        //log.info("message3 : " + type);
        if ("enter".equals(type)) {
            String chatRoomId = jsonMessage.getString("chatRoomId");
            chatUserDtoList.add(ChatUserDto.builder()
                    .chatRoomId(chatRoomId)
                    .session(session)
                    .build());
        } else if ("msg".equals(type)) {
            String senderChatRoomId = null;
            for (ChatUserDto chatUserDto : chatUserDtoList) {
                if (chatUserDto.getSession().equals(session)) {
                    senderChatRoomId = chatUserDto.getChatRoomId();
                    break;
                }
            }
            List<WebSocketSession> sessions = new ArrayList<>();
            for (ChatUserDto chatUserDto : chatUserDtoList) {
                if (chatUserDto.getChatRoomId().equals(senderChatRoomId)) {
                    sessions.add(chatUserDto.getSession());
                }
            }
            if (sessions != null) {
                for (WebSocketSession sess: sessions) {
                    if (sess.isOpen()) {
                        sess. sendMessage(message);
                    } // if e
                } // for e
            } // if e
        } // else if e

    } // method e

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Transport error: " + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatUserDtoList.removeIf(chatUserDto -> chatUserDto.getSession().equals(session));
    }

}