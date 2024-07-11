package com.bookshopping.bookshopping.Repository;

import com.bookshopping.bookshopping.Model.OrderedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderedBookRepository extends JpaRepository<OrderedBook, Long> {
}
