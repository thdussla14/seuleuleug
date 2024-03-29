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
    protected static List<ChatUserDto> chatUserDtoList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("서버에 접속");
        log.info("session1111: " + session);
        String chatRoomId = (String) session.getAttributes().get("pathes");
        log.info("chatRoomId: " + chatRoomId);
        chatUserDtoList.add(ChatUserDto.builder()
                        .session(session)
                        .chatRoomId(chatRoomId)
                        .sessionId(session.getId())
                        .build());
        log.info("서버 접속 : chatUserDtoList :" + chatUserDtoList);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("message2: " + message.getPayload());
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type");

        if ("enter".equals(type)) {
            for (ChatUserDto chatUserDto : chatUserDtoList) {
                if(chatUserDto.getSessionId().equals(session.getId())){
                    chatUserDto.setType(jsonMessage.getString("who"));
                }
                if(chatUserDto.getChatRoomId().equals((String) session.getAttributes().get("pathes"))){
                    JSONObject payload = new JSONObject();
                    if ("normal".equals(jsonMessage.getString("who"))){
                        payload.put("message", "회원이 입장하였습니다.");
                    }else if("doctor".equals(jsonMessage.getString("who"))){
                        payload.put("message", "의사가 입장하였습니다.");
                    }
                    TextMessage textMessage = new TextMessage(payload.toString());
                    chatUserDto.getSession().sendMessage(textMessage);
                }
            }
        } else if ("msg".equals(type)) {
            String senderChatRoomId = (String) session.getAttributes().get("pathes");
            List<WebSocketSession> sessions = new ArrayList<>();

            for (ChatUserDto chatUserDto : chatUserDtoList) {
                if (chatUserDto.getChatRoomId().equals(senderChatRoomId)) {
                    sessions.add(chatUserDto.getSession());
                }
            }

            if (sessions != null) {
                for (WebSocketSession sess : sessions) {
                    if (sess.isOpen()) {
                        sess.sendMessage(message);
                    }
                }
            }
        } // msg e
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Transport error: " + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("채팅 서버에서 나감");
        JSONObject payload = new JSONObject();
        String chatRoomId = null;
        for (ChatUserDto chatUserDto : chatUserDtoList) {
            if(chatUserDto.getSession().equals(session)){
                if("normal".equals(chatUserDto.getType())){
                    payload.put("message", "회원이 퇴장하였습니다.");
                }else if("doctor".equals(chatUserDto.getType())){
                    payload.put("message", "의사가 퇴장하였습니다.");
                }
                chatRoomId = chatUserDto.getChatRoomId();
            }
        }
        log.info("리스트 삭제 전 : chatUserDtoList :" + chatUserDtoList);
        chatUserDtoList.removeIf(chatUserDto -> chatUserDto.getSessionId().equals(session.getId()));
        log.info("리스트 삭제 후 : chatUserDtoList :" + chatUserDtoList);
        for (ChatUserDto chatUserDto : chatUserDtoList) {
            if(chatUserDto.getChatRoomId().equals(chatRoomId)){
                TextMessage textMessage = new TextMessage(payload.toString());
                chatUserDto.getSession().sendMessage(textMessage);
            }
        }
    }

    public List<ChatUserDto> getChatUserDtoList() {
        log.info("getChatUserDtoList");
        List<ChatUserDto> result = new ArrayList<>();
        for (ChatUserDto dto : chatUserDtoList) {
            ChatUserDto newDto = ChatUserDto.builder()
                    .sessionId(dto.getSessionId())
                    .chatRoomId(dto.getChatRoomId())
                    .type(dto.getType())
                    .build();
            result.add(newDto);
        }
        return result;
    }

}