package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartRequest {
    private Long userId;
    private Long bookId;
    private Integer count;

}
