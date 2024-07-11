package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.BorrowDto;
import OnlineEBookStore.demo.Dto.OrderDto;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.*;
import OnlineEBookStore.demo.Repository.*;
import OnlineEBookStore.demo.Request.BorrowRequest;
import OnlineEBookStore.demo.Request.NotificationRequest;
import OnlineEBookStore.demo.Response.BorrowResponse;
import OnlineEBookStore.demo.Response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class BorrowService {
    @Autowired
    private BorrowDto borrowDto;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository BookRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private  NotificationRepository notificationRepository;

    public List<BorrowResponse> findAll() {
        List<Borrow> userHistoryList = borrowRepository.findAll();

        return borrowDto.mapToUserResponse(userHistoryList);

    }

    public List<Notification> findall() {
        return notificationRepository.findAll();
    }
    public List<Notification> getNotification(){
        return notificationRepository.notifications();
    }
    @Transactional
    public List<BorrowResponse> issueBook(BorrowRequest borrowRequest) {

        Borrow  borrow = borrowDto.mapToBook(borrowRequest);
        CommonUser commonUser;
        Book book;

        commonUser = userRepository.findById(borrowRequest.getUserId())
                .orElseThrow(() -> new ResoucreNotFoundException("UserId",
                        "UserId", borrowRequest.getUserId()));

        book = BookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new ResoucreNotFoundException("BookId",
                        "BookId", borrowRequest.getBookId()));
        borrow.setCommonUser(commonUser);
        borrow.setBook(book);
        borrow.setReturned(false);
        borrowRepository.save(borrow);
        return findAll();

    }


    public List<BorrowResponse> returnBook(BorrowRequest borrowRequest) {

        Borrow borrow = borrowDto.mapToBook(borrowRequest);
        CommonUser commonUser;
        Book book;

        commonUser = userRepository.findById(borrowRequest.getUserId())
                .orElseThrow(() -> new ResoucreNotFoundException("UserId",
                        "UserId", borrowRequest.getUserId()));

        book = BookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new ResoucreNotFoundException("BookId",
                        "BookId", borrowRequest.getBookId()));
        Address address = addressRepository.findById((borrowRequest.getAddressId()))
                .orElseThrow(() ->
                        new ResoucreNotFoundException("addressId", "addressId", borrowRequest.getAddressId()));
        borrow.setCommonUser(commonUser);
        borrow.setBook(book);
        borrowRepository.save(borrow);
        return findAll();

    }


    public List<BorrowResponse> findById(Long userId) {
        List<Borrow> borrowList = borrowRepository.findByUserId(userId);
        return borrowDto.mapToUserResponse(borrowList);


    }
    public String returnBooks(Long id){
        borrowRepository.returnBook(id);
        return "success";
    }
    public List<Borrow> returnedBook(){
        List<Borrow> returnedBook= borrowRepository.returnedBooks();
        return  returnedBook;
    }


    public List<Notification> Request(NotificationRequest notificationRequest) {
        Notification notifications = new Notification();


        CommonUser appuser = userRepository.findById(notificationRequest.getUserId())
                .orElseThrow(() -> new ResoucreNotFoundException("userId",
                        "userId", notificationRequest.getUserId()));

        Book book = BookRepository.findById(notificationRequest.getBookId())
                .orElseThrow(() -> new ResoucreNotFoundException("bookId",
                        "bookId", notificationRequest.getBookId()));

        notifications.setCommonUser(appuser);
        notifications.setBook(book);
        notifications.setRequest(true);
       notificationRepository.save(notifications);

        return findall();
    }
    public List<Borrow> getOverdueBorrows() {
        LocalDate currentDate = LocalDate.now();
        return borrowRepository.findByReturnDateBeforeAndReturnedFalse(currentDate);
    }

}
