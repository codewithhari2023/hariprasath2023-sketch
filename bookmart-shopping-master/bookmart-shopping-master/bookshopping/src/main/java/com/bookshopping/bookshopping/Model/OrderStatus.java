package com.bookshopping.bookshopping.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String status;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public OrderStatus(String status) {
        this.status = status;
    }
}

