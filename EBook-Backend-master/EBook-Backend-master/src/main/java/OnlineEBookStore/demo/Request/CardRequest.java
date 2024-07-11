package OnlineEBookStore.demo.Request;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
        private  Long id;
        private Long userId;
        private String cardNumber;
        private Integer cvv;
        private  Integer expirydate;
        private Integer balance;
}
