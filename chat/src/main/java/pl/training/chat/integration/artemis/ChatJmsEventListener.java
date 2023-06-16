package pl.training.chat.integration.artemis;

import jakarta.jms.Message;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.training.chat.ChatMessageDto;

@Service
@Log
public class ChatJmsEventListener {

    @JmsListener(destination = "messages", containerFactory = "trainingJmsContainerFactory")
    @SneakyThrows
    public void onMessage(Message message) {
        var chatMessageDto = message.getBody(ChatMessageDto.class);
        log.info("New JMS message: " + chatMessageDto);
    }

}
