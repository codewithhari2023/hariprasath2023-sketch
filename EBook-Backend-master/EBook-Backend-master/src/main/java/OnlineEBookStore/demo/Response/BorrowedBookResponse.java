package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.BorrowedBook;
import OnlineEBookStore.demo.Model.OrderedBook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class BorrowedBookResponse {
    private Long userId;
    private Long id;
    private List<BorrowedBook> bookList;
    private Long userid;
    private String username;
    private Address Deliveryaddress;
    private String orderStatus;
}
