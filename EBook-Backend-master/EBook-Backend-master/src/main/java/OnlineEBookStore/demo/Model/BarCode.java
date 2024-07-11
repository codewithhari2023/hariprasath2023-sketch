package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "barcode")
public class BarCode {
    @Column
    @Id
    @GeneratedValue
    private  Long id;

    private Long Book_id;

    private byte[] imageData;


}
