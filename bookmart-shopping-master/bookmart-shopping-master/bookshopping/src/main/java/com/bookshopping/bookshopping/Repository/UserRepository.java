package com.bookshopping.bookshopping.Repository;

import com.bookshopping.bookshopping.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query("select e from #{#entityName} e where e.username=?1")
    Optional<AppUser> findByUsername(String username);
}
