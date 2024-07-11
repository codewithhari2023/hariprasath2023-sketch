package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
   
    @Id
    @GeneratedValue
    private Long id;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private CommonUser commonUser;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
   
    private Integer count;
}
