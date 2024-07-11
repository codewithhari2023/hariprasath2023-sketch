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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderedBook")
public class OrderedBook {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column
    private String author;
    @Column
    private Double price;
    @Column
    private String description;
    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private  Order order;
    @Column
    private Integer count=1;
    @CreationTimestamp
    @Column
    private LocalDateTime orderTime;

}
