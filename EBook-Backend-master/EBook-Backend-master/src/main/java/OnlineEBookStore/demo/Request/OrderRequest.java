package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    private Long userId;
    private Long addressId;

}
