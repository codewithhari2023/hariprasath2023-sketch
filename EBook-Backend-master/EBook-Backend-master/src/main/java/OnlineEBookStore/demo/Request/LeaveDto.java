package OnlineEBookStore.demo.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class    LeaveDto {

        private String username;
        private String startDate; // Date as string
        private String endDate; // Date as string
        private String reason;

        // getters and setters


}
