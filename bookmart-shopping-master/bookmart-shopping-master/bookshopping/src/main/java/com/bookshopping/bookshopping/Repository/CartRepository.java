package com.bookshopping.bookshopping.Repository;

import com.bookshopping.bookshopping.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c inner join c.appUser a where a.id=?1")
    Optional<List<Cart>> findUserCart(Long userId);
}
