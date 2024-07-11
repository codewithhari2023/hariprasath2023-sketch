package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest,Long> {
}
