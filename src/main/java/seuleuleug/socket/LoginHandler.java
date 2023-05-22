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
        if("enter".equals(type)) { // 로그인했을때
            String loginType = jsonMessage.getString("loginType");
            for (LoginUserDto loginUserDto : loginUserDtoList) {
                if(loginUserDto.getUserEmail().equals(session.getAttributes().get("pathes"))){
                    loginUserDto.setType(loginType);
                }
            }
        }else if("counsel".equals(type)){ // 의사에게 상담 신청할 때
            String doctor = jsonMessage.getString("doctor");
            String normal = jsonMessage.getString("normal");
            log.info("normal email : " + normal);
            log.info("doctor email : " + doctor);
            for (LoginUserDto loginUserDto : loginUserDtoList) {
                if (loginUserDto.getUserEmail().equals(doctor)){
                    JSONObject payload = new JSONObject();
                    payload.put("normal", normal);
                    TextMessage textMessage = new TextMessage(payload.toString());
                    loginUserDto.getSession().sendMessage(textMessage);;
                }
            }
        }else if("answer".equals(type)){ // 의사가 상담요청에 대해 답을 줬을때
            String answer = jsonMessage.getString("answer");
            String doctor = jsonMessage.getString("doctor");
            String normal = jsonMessage.getString("normal");
            log.info("answer : " + answer);
            JSONObject payload = new JSONObject();
            payload.put("answer", answer);
            payload.put("doctor", doctor);
            TextMessage textMessage = new TextMessage(payload.toString());
            for (LoginUserDto loginUserDto : loginUserDtoList) {
                if(loginUserDto.getUserEmail().equals(normal)){
                    loginUserDto.getSession().sendMessage(textMessage);
                }
            }
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
