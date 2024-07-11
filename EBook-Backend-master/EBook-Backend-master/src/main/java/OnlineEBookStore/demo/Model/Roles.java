package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Roles {

    public static final String ADMIN = "ADMIN";
    public static final String PUBLIC = "PUBLIC";
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String role;
    public Roles(String role) {
        this.role = role;
    }

}
