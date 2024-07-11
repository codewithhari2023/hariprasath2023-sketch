package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Cart;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardReposiotry extends JpaRepository<DebitCard,Long> {
    @Query("SELECT d FROM DebitCard d WHERE d.commonUser.id = ?1")
    Optional<List<DebitCard>> findUserCards(Long userId);

}
