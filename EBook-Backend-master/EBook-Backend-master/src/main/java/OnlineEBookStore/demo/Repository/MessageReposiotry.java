package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Conversation;
import OnlineEBookStore.demo.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageReposiotry extends JpaRepository<Messages,Long> {
    List<Messages> findAllByConversation(Conversation conversation);

    void deleteAllByConversation(Conversation conversation);
}
