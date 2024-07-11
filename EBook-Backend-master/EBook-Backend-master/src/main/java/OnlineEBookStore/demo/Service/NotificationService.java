package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Notification;
import OnlineEBookStore.demo.Repository.BookRepository;
import OnlineEBookStore.demo.Repository.NotificationRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public List<Notification> Request(NotificationRequest notificationRequest) {
        Notification notifications = new Notification();


        CommonUser commonUser = userRepository.findById(notificationRequest.getUserId())
                .orElseThrow(() -> new ResoucreNotFoundException("userId",
                        "userId", notificationRequest.getUserId()));

        Book book = bookRepository.findById(notificationRequest.getBookId())
                .orElseThrow(() -> new ResoucreNotFoundException("bookId",
                        "bookId", notificationRequest.getBookId()));

        notifications.setCommonUser(commonUser);
        notifications.setBook(book);
        notifications.setRequest(true);
        notificationRepository.save(notifications);

        return findAll();
    }

    public String clear(Long id) {
        notificationRepository.clearNotification(id);
        return "success";
    }
    public List<Notification> getNotification(){
        return notificationRepository.notifications();
    }
    public void sendPenaltyNotification(CommonUser user, BigDecimal penaltyAmount) {
        String message = "You have a penalty of $" + penaltyAmount + ". Please clear it to avoid further actions.";
        Notification notification = new Notification();
        notification.setCommonUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }


}
