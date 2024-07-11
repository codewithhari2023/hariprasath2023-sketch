package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.OrderedBook;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderResponse {
    private Long userId;
    private Long id;
    private List<OrderedBook> bookList;
    private Long userid;
    private String username;
    private Address Deliveryaddress;
    private String orderStatus;
}
