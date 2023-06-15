package pl.training.chat;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemMessageSender {

    private static final String SYSTEM_SENDER = "System";

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TimeProvider timeProvider;
    @Value("${main-room}")
    @Setter
    private String mainRoom;

    public void send(String text) {
        var message = ChatMessageDto.builder()
                .sender(SYSTEM_SENDER)
                .text(text)
                .timestamp(timeProvider.getTime())
                .build();
        simpMessagingTemplate.convertAndSend(mainRoom, message);
    }

}
