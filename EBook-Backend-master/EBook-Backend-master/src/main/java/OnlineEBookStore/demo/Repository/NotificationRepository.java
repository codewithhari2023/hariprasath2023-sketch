package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Modifying
    @Query("UPDATE Notification n SET n.request = false WHERE n.id = ?1")
    void clearNotification(Long id);



    @Query("SELECT n FROM Notification n WHERE n.request = true")
    List<Notification> notifications();

}
