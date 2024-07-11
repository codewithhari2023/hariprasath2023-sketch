package OnlineEBookStore.demo.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaveRequestforUser {
    private  String startDate;
    private String endDate;
    private  String Reason;
}
