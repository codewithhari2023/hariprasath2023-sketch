package OnlineEBookStore.demo.Response;

import OnlineEBookStore.demo.Request.CardRequest;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardResponse {
   private  Long id;
   private Long userId;
   private String CardNumber;
   private Integer cvv;
   private  Integer expirydate;
   private Integer balance;
}
