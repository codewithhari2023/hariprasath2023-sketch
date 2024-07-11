package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "excelupload")
public class ExcelUpload {
    @Column
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String uploadFileName;
    @Column
    @CreationTimestamp
    private Timestamp uploadAt;
    @OneToOne
    @JoinColumn(name = "Role_id",referencedColumnName = "id")
    private Roles roles;
    @Column
    private String role;
    @Column
    private String path;
}
