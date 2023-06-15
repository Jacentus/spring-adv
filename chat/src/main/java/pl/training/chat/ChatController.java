package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Log
@RequiredArgsConstructor
public class ChatController {

    private final TimeProvider timeProvider;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Value("${main-room}")
    @Setter
    private String mainRoom;
    @Value("${private-rooms-prefix}")
    @Setter
    private String privateRoomsPrefix;

    @MessageMapping("/chat")
    public void onMessage(@Payload ChatMessageDto chatMessageDto, @Header("simpSessionId") String simpSessionId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        var attributes = simpMessageHeaderAccessor.getSessionAttributes();
        // var user = attributes.get(simpSessionId);
        // var clientId = attributes.get("clientId");

        var response = chatMessageDto.withTimestamp(timeProvider.getTime());
        log.info("New message: " + chatMessageDto);
        if (chatMessageDto.isBroadcast()) {
            simpMessagingTemplate.convertAndSend(mainRoom, response);
        } else {
            simpMessagingTemplate.convertAndSend(privateRoomsPrefix + response.getSender(), response);
            chatMessageDto.getRecipients()
                    .forEach(recipient -> simpMessagingTemplate.convertAndSend(privateRoomsPrefix + recipient, response));
        }

    }

    /*@MessageMapping("/chat")
    @SendTo("/main-room")
    public ChatMessageDto onMessage(ChatMessageDto chatMessageDto) {
        var response = chatMessageDto.withTimestamp(timeProvider.getTime());
        log.info("New message: " + chatMessageDto);
        return response;
    }*/

}
