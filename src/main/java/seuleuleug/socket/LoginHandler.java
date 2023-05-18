package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import seuleuleug.domain.Chatting.LoginUserDto;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class LoginHandler extends TextWebSocketHandler {
    @Autowired
    private ChattingHandler chattingHandler;

    private static List<LoginUserDto> loginUserDtoList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("로그인 서버 접속");
        log.info("session1111: " + session);
        String email = (String) session.getAttributes().get("pathes");
        log.info("user email: " + email);
        loginUserDtoList.add(LoginUserDto.builder()
                        .session(session)
                        .userEmail(email)
                        .build());
        log.info("loginUserDtoList : " + loginUserDtoList);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("로긴핸들러에 메세지 들어옴 : " + message.getPayload());
        String LoginType = (String) message.getPayload();
        for (LoginUserDto loginUserDto : loginUserDtoList) {
            if(loginUserDto.getUserEmail().equals(session.getAttributes().get("pathes"))){
                loginUserDto.setType(LoginType);
            }
        }
        log.info("loginUserDtoList : " + loginUserDtoList);
        handleTextMessage(session, (TextMessage) message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /*
        log.info("get message : " + message.getPayload());
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        log.info("jsonMessage : " + jsonMessage );
        String type = jsonMessage.getString("type");
        log.info("type : " + type );
        if("login".equals(type)) { // 로그인 한 경우
            for (LoginUserDto dto : loginUserDtoList) {
                if(dto.getSession().equals(session)){
                    dto.setType(jsonMessage.getString("who"));
                    dto.setUserEmail(jsonMessage.getString("userEmail"));
                } // if e
            } // for e
        }else if("request".equals(type)){ // 일반 유저가 의사에게 상담 요청을 보낸 경우
            log.info("request 로 옴");
            for (LoginUserDto dto : loginUserDtoList) {
                if (dto.getUserEmail().equals(jsonMessage.getString("userEmail")));
                    String tochatmessage = " type : enter, chatRoomId : 123 ";
                    TextMessage textMessage = new TextMessage(tochatmessage);
                    chattingHandler.handleTextMessage(dto.getSession(),textMessage);

            }
        }*/
        log.info("get message : " + message);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("로그인 서버 에러");
        log.info("exception : " + exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("로그인 서버 닫힘");
        log.info("loginUserDtoList : " + loginUserDtoList);
        loginUserDtoList.removeIf(loginUserDto -> loginUserDto.getSession().equals(session));
        log.info("loginUserDtoList : " + loginUserDtoList);
    }

}
