package OnlineEBookStore.demo.Repository;

import OnlineEBookStore.demo.Model.Address;
import OnlineEBookStore.demo.Model.Cart;
import OnlineEBookStore.demo.Model.CommonUser;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}
