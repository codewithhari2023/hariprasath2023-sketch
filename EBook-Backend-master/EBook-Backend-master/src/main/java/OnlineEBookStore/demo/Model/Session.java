package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue
    @Column
    private  Long id;
    @Column
    @CreationTimestamp
    private Timestamp SessionStart;
    @Column
    @UpdateTimestamp
    private  Timestamp SessionEnd;
    @OneToOne
    @JoinColumn
    private CommonUser commonUser;

}
