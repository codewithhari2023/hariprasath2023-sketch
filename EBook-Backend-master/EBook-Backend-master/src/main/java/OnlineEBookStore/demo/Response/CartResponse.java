package OnlineEBookStore.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long userId;
    private Long bookId;
    private Integer count;
}
