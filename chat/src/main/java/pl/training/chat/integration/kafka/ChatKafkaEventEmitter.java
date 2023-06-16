package pl.training.chat.integration.kafka;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.training.chat.ChatEventEmitter;
import pl.training.chat.ChatMessageDto;

@Service
@RequiredArgsConstructor
public class ChatKafkaEventEmitter implements ChatEventEmitter {

    private final KafkaTemplate<String, ChatMessageDto> kafkaTemplate;

    @Value("${messages-topic}")
    @Setter
    private String topic;

    @Override
    public void emit(ChatMessageDto chatMessageDto) {
        kafkaTemplate.send(topic, chatMessageDto);
    }

}
