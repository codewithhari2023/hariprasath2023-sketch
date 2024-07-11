package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.BarCode;
import OnlineEBookStore.demo.Model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookBarcodeRequest {
    private List<Book> books;
}
