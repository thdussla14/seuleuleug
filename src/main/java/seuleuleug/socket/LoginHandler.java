package seuleuleug.socket;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
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
        log.info("서버 접속");
        loginUserDtoList.add(LoginUserDto.builder()
                        .session(session)
                        .build());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 송신 : " + message.getPayload());
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type");
        if("login".equals(type)) { // 로그인 한 경우
            for (LoginUserDto dto : loginUserDtoList) {
                if(dto.getSession().equals(session)){
                    dto.setType(jsonMessage.getString("who"));
                    dto.setUserEmail(jsonMessage.getString("userEmail"));
                } // if e
            } // for e
        }else if("request".equals(type)){ // 일반 유저가 의사에게 상담 요청을 보낸 경우
            for (LoginUserDto dto : loginUserDtoList) {
                if (dto.getUserEmail().equals(jsonMessage.getString("userEmail")));


            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("서버 에러");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("서버 닫힘");
        log.info("loginUserDtoList : " + loginUserDtoList);
        loginUserDtoList.removeIf(loginUserDto -> loginUserDto.getSession().equals(session));
        log.info("loginUserDtoList : " + loginUserDtoList);
    }
}
