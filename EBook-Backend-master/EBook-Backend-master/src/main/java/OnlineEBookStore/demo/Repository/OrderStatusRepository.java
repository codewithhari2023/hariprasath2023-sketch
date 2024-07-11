package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<Status,Long> {
    Optional<Status> findById(long l);

    List<Status> findAll();
    Status findByStatus(String status);
}
