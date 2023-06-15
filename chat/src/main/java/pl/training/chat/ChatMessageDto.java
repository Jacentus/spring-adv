package pl.training.chat;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@Builder
@Value
public class ChatMessageDto {

    String sender;
    Set<String> recipients;
    String text;
    @With
    String timestamp;

    public boolean isBroadcast() {
        return recipients == null || recipients.isEmpty();
    }

}
