package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BorrowRequest {
    private Long Id;
    private Long userId;
    private Long BookId;
    private Long addressId;

    private String issuedDate;
    private String returnDate;
}
