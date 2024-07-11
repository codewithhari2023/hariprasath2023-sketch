package OnlineEBookStore.demo.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotNull
    private Long id;
    private Long userId;
    private String city;
    private String street;
    private Integer zipcode;
}
