package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BorrowedBookRequest {

    private Long id;
    private Long userId;
    private Long bookId;
    private Long addressId;
    private Long statusId;

}
