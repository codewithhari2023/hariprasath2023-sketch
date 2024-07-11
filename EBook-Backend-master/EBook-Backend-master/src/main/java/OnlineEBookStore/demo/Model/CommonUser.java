package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class CommonUser {
    @Id
    @GeneratedValue
    @Column
    private  Long id;
    @Column
    private String name;
    @Column
    private  String password;
    @Column
    private String username;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Roles role;
    @JsonIgnore
    @OneToMany(mappedBy = "commonUser")
    private List<Order> orderList = new ArrayList<>();
@JsonIgnore
    @OneToMany(mappedBy = "commonUser")
    private List<Cart> carts;

    @OneToMany(mappedBy = "commonUser")
    private List<Address> address;
      @OneToOne
    private DebitCard card;
    private LocalDateTime loginTime;
    private String ipAddress;
    private boolean mfaEnabled;
    private String secret;
}
