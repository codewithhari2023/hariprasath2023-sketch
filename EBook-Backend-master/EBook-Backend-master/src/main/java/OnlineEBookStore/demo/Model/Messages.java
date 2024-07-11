package OnlineEBookStore.demo.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Messages {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private CommonUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private CommonUser receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}