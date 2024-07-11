package OnlineEBookStore.demo.Request;

import lombok.*;

import java.util.Currency;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChargeRequest {
    private String description;
    private int amount; // cents
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
}
