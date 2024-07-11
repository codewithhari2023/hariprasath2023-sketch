package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {


    List<Book> findByTitleContainingIgnoreCase(String title);

}
