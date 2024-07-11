package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class BookResponse {
    private List<Book> books;
}
