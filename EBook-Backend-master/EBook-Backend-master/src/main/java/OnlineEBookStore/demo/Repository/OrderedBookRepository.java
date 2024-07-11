package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.OrderedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedBookRepository extends JpaRepository<OrderedBook,Long> {
}
