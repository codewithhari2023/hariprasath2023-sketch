package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook,Long> {
}
