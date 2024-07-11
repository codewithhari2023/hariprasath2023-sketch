package com.bookshopping.bookshopping.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderedBook> orderedBook;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatus orderStatus;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderTime;
}
