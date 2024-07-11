package com.bookshopping.bookshopping.Service;


import com.bookshopping.bookshopping.Dto.OrderDto;
import com.bookshopping.bookshopping.Exception.ResourceNotFoundException;
import com.bookshopping.bookshopping.Model.*;
import com.bookshopping.bookshopping.Repository.*;
import com.bookshopping.bookshopping.Response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderedBookRepository orderedBookRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Transactional
    public List<OrderResponse> placeOrder(Long userId, Long addressId) {
        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId", "userId", userId));

        Address address = addressRepository.findById((addressId))
                .orElseThrow(() ->
                        new ResourceNotFoundException("addressId", "addressId", addressId));

        OrderStatus orderStatus = orderStatusRepository.findById(1L)
                .orElseThrow(() ->
                        new ResourceNotFoundException("orderStatusId", "orderStatusId", 1));

        List<Cart> cartList = cartRepository.findUserCart(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId", "userId", userId));

        Orders order = new Orders();
        order.setAddress(address);
       order.setOrderStatus(orderStatus);
        order.setAppUser(appUser);

        order = orderRepository.save(order);

        for (Cart cart : cartList) {
            OrderedBook orderedBook = new OrderedBook();
            orderedBook.setOrder(order);
            orderedBook.setTitle(cart.getBook().getTitle());
            orderedBook.setAuthor(cart.getBook().getAuthor());
            orderedBook.setDescription(cart.getBook().getDescription());
            orderedBook.setPrice(cart.getBook().getPrice());
            orderedBook.setCount(cart.getCount());
            orderedBookRepository.save(orderedBook);
            cartRepository.delete(cart);
        }

        return getUserOrders(userId);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<Orders> orderList = orderRepository.findUserOrders(userId)
                .orElseThrow(() -> new ResourceNotFoundException("userId", "userId", userId));
        return orderDto.mapToOrderResponse(orderList);
    }

    public List<OrderResponse> getAllOrders() {
        List<Orders> orderList = orderRepository.findAll();
        return orderDto.mapToOrderResponse(orderList);
    }

    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }

    public List<OrderResponse> updateOrderStatus(Long orderId, Long statusId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("orderId", "orderId", orderId));

        OrderStatus orderStatus = orderStatusRepository.findById(statusId)
                .orElseThrow(() -> new ResourceNotFoundException("statusId", "statusId", statusId));

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        return getAllOrders();
    }
}