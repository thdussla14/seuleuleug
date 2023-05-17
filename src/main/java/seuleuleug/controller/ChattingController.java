package seuleuleug.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seuleuleug.domain.Chatting.ChatUserDto;
import seuleuleug.socket.ChattingHandler;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/chatting")
public class ChattingController {

    @Autowired
    private ChattingHandler chattingHandler;

    @GetMapping("/chatlist")
    public List<ChatUserDto> getChattingList(){
        List<ChatUserDto> result = chattingHandler.getChatUserDtoList();
        log.info(result.toString());
        return result;
    }
}
