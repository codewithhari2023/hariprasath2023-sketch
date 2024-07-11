package com.bookshopping.bookshopping.Repository;

import com.bookshopping.bookshopping.Model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByStatus(String status);
}
