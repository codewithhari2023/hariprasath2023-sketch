package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.Book;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExcelRequest {
    private String filename;
    private List<Book>books;
}
