package pl.training.chat.integration.kafka;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.training.chat.ChatMessageDto;

@Service
@Log
public class ChatKafkaEventListener {

    @KafkaListener(topics = "messages")
    public void onMessage(ChatMessageDto chatMessageDto) {
        log.info("New Kafka message: " + chatMessageDto);
    }

}
