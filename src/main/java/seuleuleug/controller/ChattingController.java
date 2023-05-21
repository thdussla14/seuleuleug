package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.Chatting.ChatUserDto;
import seuleuleug.domain.Chatting.LoginUserDto;
import seuleuleug.socket.ChattingHandler;
import seuleuleug.socket.LoginHandler;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chatting")
public class ChattingController {

    @Autowired
    private ChattingHandler chattingHandler;
    @Autowired
    private LoginHandler loginHandler;

    @GetMapping("/chatlist")
    public List<ChatUserDto> getChattingList(){
        List<ChatUserDto> result = chattingHandler.getChatUserDtoList();
        log.info(result.toString());
        for (ChatUserDto dto : result) {
            dto.setSession(null);
        }
        return result;
    }

    @GetMapping("/logindoctor")
    public List<LoginUserDto> getLoginDoctor(){
        List<LoginUserDto> result = new ArrayList<>();
        for (LoginUserDto dto : loginHandler.loginUserDtoList) {
            if("doctor".equals(dto.getType())){
                result.add(LoginUserDto.builder()
                        .type(dto.getType())
                        .userEmail(dto.getUserEmail())
                        .build());
            }
        }
        log.info("result : " + result.toString());
        return result;
    }
}
