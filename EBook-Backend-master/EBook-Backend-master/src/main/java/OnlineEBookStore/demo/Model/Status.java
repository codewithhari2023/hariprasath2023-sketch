package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderStatus")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String status;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn
    private  BorrowedBook borrowedBook;
    @OneToOne
    @JoinColumn
    private  OrderedBook orderedBook;



}
