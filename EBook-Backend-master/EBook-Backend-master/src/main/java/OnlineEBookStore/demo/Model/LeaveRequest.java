package OnlineEBookStore.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leaveRequest")
public class LeaveRequest {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String LeaveStart;
    @Column
    private String LeaveEnd;
    @Column
    private String Reason;
    private  String status;
    private String username;


}
