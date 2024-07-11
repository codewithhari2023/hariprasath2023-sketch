package OnlineEBookStore.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrowedbook")
public class BorrowedBook {
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
    @Column
    private Integer count;
    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<CommonUser>commonUsers;
    @OneToOne
    @JoinColumn(name = "book")
    private Book book;

    @ManyToOne
    @JoinColumn
    private Borrow borrow;
    @Column
    private LocalDateTime returndate;

}
