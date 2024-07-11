package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @ManyToOne
    @JoinColumn(name = "addresss_id")
    private Address DeliveryAddress;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private CommonUser commonUser;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderedBook>orderedBooks;
    @OneToOne
    @JoinColumn(name = "Status_id")
    private Status status;



}
