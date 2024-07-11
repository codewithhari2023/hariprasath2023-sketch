package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Long> {
    @Query("select c from Cart c inner join c.commonUser a where a.id=?1")
    Optional<List<Cart>> findUserCart(Long userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE book.id=:bookId AND commonUser.id=:userId")
    void deleteCart(Long bookId, Long userId);
}
