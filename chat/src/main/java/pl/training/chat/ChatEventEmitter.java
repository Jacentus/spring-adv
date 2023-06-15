package pl.training.chat;

public interface ChatEventEmitter {

    void emit(ChatMessageDto chatMessageDto);

}
