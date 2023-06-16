package pl.training.chat;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto implements Serializable {

    private String sender;
    private Set<String> recipients;
    private String text;
    @With
    private String timestamp;

    public boolean isBroadcast() {
        return recipients == null || recipients.isEmpty();
    }

}
