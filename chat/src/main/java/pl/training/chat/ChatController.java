package pl.training.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    public ChatMessageDto onMessage(ChatMessageDto chatMessageDto) {
        var response = chatMessageDto.withTimestamp()
    }

}
