package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow")
public class Borrow {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn
    private CommonUser commonUser;
    @OneToOne
    @JoinColumn
    private Status status;
    @ManyToOne
    @JoinColumn
    private Address address;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private String issueDate;
    private Boolean returned;
    private  String returnDate;
    private BigDecimal penaltyAmount = BigDecimal.ZERO;
    private boolean penaltyDeducted = false;
}
