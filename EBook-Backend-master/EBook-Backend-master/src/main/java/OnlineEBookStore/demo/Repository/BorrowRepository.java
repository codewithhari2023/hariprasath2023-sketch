package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Long> {
    @Query("SELECT b FROM Borrow b JOIN b.commonUser c WHERE c.id = :userId")
    List<Borrow> findByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Borrow b WHERE b.returned = true")
    List<Borrow> returnedBooks();



    @Transactional
    @Modifying
    @Query("UPDATE Borrow b SET b.returned = true WHERE b.id = :id")
    void returnBook(@Param("id") Long id);


    List<Borrow> findByReturnDateBeforeAndReturnedFalse(LocalDate returnDate);
}
