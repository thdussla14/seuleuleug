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

    public static List<LoginUserDto> loginUserDtoList = new ArrayList<>();

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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
     log.info("로긴핸들러에 메세지 들어옴 : " + message.getPayload());
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type");
        if("enter".equals(type)) {
            String loginType = jsonMessage.getString("loginType");
            for (LoginUserDto loginUserDto : loginUserDtoList) {
                if(loginUserDto.getUserEmail().equals(session.getAttributes().get("pathes"))){
                    loginUserDto.setType(loginType);
                }
            }
        }else if("counsel".equals(type)){
            String toEmail = jsonMessage.getString("toEmail");
            String receiveEmail = jsonMessage.getString("receiveEmail");
            log.info("receiveEmail : " + receiveEmail);
            log.info("toEmail : " + toEmail);
        }

        log.info("loginUserDtoList : " + loginUserDtoList);

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
