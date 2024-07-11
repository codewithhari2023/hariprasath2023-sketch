package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserCard")
public class DebitCard {
    @Id
    @GeneratedValue
    private  Long id;
    @Column
    private String CardNumber;
    @Column
    private Integer cvv;
    @Column
    private  Integer expirydate;
    @Column
    private BigDecimal balance;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private CommonUser commonUser;

}
