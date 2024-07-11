package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.CommonUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CommonUser,Long> {
    @Query("select c from  CommonUser c where c.username=?1 ")
    Optional<CommonUser> findByUserName(String title);
    @Query("select c from  CommonUser c where c.card=?1 ")
    Optional<CommonUser> findByUsercard(Long id);

   CommonUser findByUsername(String username);
    @Query("SELECT c FROM CommonUser c WHERE c.id <> ?1")
    List<CommonUser> findAllUsersExceptThisUserId(Long userId);

//    CommonUser findByUserame(String username);

}
