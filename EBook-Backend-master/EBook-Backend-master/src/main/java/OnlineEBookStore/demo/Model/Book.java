package OnlineEBookStore.demo.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private Double price;
    @Column
    private String description;
    @Column
    private String photo;
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private  Category category;

@JsonIgnore
    @OneToMany(mappedBy = "book",cascade=CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();


    public Book(String title, int categoryId, String author, double price, String description) {
    }

    public Book( String title, String description, String author) {
    }
}
