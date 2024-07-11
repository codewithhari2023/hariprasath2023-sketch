package OnlineEBookStore.demo.Model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private CommonUser user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private CommonUser user2;
}

