package OnlineEBookStore.demo.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressResponse {
    private Long id;
    private Long userId;
    private String city;
    private String street;
    private Integer zipcode;
}
