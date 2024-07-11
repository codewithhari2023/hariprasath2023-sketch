package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private Integer zipcode;
   @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CommonUser commonUser;
     @JsonIgnore
    @OneToMany(mappedBy = "DeliveryAddress")
    private List<Order> order;
     @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Borrow> borrows;
    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
