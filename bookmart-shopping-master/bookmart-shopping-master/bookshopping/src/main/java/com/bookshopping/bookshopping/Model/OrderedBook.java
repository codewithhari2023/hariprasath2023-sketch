package com.bookshopping.bookshopping.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders order;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 200)
    private String author;

    @Column(nullable = false, length = 200)
    private Double price;

    private Integer count = 1;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderTime;
}
