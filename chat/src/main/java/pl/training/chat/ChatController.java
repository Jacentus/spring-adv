package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Log
@RequiredArgsConstructor
public class ChatController {

    private final TimeProvider timeProvider;

    @MessageMapping("/chat")
    @SendTo("/main-room")
    public ChatMessageDto onMessage(ChatMessageDto chatMessageDto) {
        var response = chatMessageDto.withTimestamp(timeProvider.getTime());
        log.info("New message: " + chatMessageDto);
        return response;
    }

}
