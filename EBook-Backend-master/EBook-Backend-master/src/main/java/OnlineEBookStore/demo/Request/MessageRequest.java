package OnlineEBookStore.demo.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long conversationId;
    private String message;
    private Long receiverId;
    private Long senderId;
    private LocalDateTime timestamp;


}
