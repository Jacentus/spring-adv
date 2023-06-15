package pl.training.chat.integration.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.training.chat.ChatEventEmitter;
import pl.training.chat.ChatMessageDto;

@Service
@Log
@RequiredArgsConstructor
public class ChatMailEventEmitter implements ChatEventEmitter {

    @Override
    public void emit(ChatMessageDto chatMessageDto) {

    }

}
