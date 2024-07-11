    package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Conversation;
import OnlineEBookStore.demo.Response.ConversationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {
    @Query("SELECT c FROM Conversation c WHERE (c.user1 = :user1 AND c.user2 = :user2) OR (c.user1 = :user2 AND c.user2 = :user1)")
    Optional<Conversation> findConversationByUsers(@Param("user1") CommonUser user1, @Param("user2") CommonUser user2);




    @Query(
            nativeQuery = true,
            value = """
    SELECT
        C.conversation_id AS conversationId,
        other_user.user_id AS otherUserId,
        CONCAT(other_user.username, ' ', other_user.name) AS otherUserName,
        last_message.message AS lastMessage,
        last_message.timestamp AS lastMessageTimestamp
    FROM conversation AS C
    INNER JOIN user AS other_user
        ON (C.user1_id = other_user.user_id OR C.user2_id = other_user.user_id)
        AND other_user.user_id != ?1
    LEFT JOIN (
        SELECT
            M.conversation_id,
            M.message,
            M.timestamp
        FROM message M
        WHERE M.timestamp = (
            SELECT MAX(M2.timestamp)
            FROM message M2
            WHERE M2.conversation_id = M.conversation_id
        )
    ) AS last_message
    ON C.conversation_id = last_message.conversation_id
    WHERE C.user1_id = ?1 OR C.user2_id = ?1
    ORDER BY last_message.timestamp DESC;  -- Changed the ORDER BY clause to sort by timestamp in descending order
    """
    )
    List<ConversationResponse> findConversationsByUserId(Long userId);

}
