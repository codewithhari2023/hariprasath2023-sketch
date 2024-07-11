package OnlineEBookStore.demo.Request;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.CommonUser;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExcelUSerRequest {
    private List<CommonUser> commonUsers;
    private String filename;
}
