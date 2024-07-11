package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select o from Order o inner join o.commonUser a where a.id=?" +
            "1")
    Optional<List<Order>> findUserOrders(Long userId);
}
