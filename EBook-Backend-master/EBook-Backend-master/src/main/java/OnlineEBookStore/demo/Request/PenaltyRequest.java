package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PenaltyRequest {
    private  String email;
    private  String issueDate;
    private  String returnDate;
}
