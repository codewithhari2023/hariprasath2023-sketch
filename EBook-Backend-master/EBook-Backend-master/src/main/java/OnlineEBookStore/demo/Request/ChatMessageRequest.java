package OnlineEBookStore.demo.Request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageRequest {

    private MessageType type;
    private String content;
    private String sender;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
