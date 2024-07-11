package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.BorrowedBook;
import OnlineEBookStore.demo.Model.OrderedBook;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BorrowResponse {
    private Long id;
    private String name;
    private String book;
    private String issuedDate;
    private String returnDate;
    private Address address;
    private boolean returned;
}
