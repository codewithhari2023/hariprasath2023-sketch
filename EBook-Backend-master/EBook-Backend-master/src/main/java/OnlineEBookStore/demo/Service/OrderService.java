package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.OrderDto;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.*;
import OnlineEBookStore.demo.Repository.*;
import OnlineEBookStore.demo.Response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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
    @Autowired
    private  EmailService emailService;
@Autowired
private  CardReposiotry cardReposiotry;
    @Transactional
    public List<OrderResponse> placeOrder(Long userId,Long addressId) {
        CommonUser commonUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", userId));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResoucreNotFoundException("addressId", "addressId", addressId));

        Status orderStatus = orderStatusRepository.findById(1L)
                .orElseThrow(() -> new ResoucreNotFoundException("orderStatusId", "orderStatusId", 1));

        List<Cart> cartList = cartRepository.findUserCart(userId)
                .orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", userId));

        List<DebitCard> debitCards = cardReposiotry.findUserCards(userId)
                .orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", userId));

        Order order = new Order();
        order.setDeliveryAddress(address);
        order.setStatus(orderStatus);
        order.setCommonUser(commonUser);

        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Cart cart : cartList) {
            OrderedBook orderedBook = new OrderedBook();
            orderedBook.setOrder(order);
            orderedBook.setTitle(cart.getBook().getTitle());
            orderedBook.setAuthor(cart.getBook().getAuthor());
            orderedBook.setDescription(cart.getBook().getDescription());
            orderedBook.setPrice(cart.getBook().getPrice());
            orderedBook.setCount(cart.getCount());

            orderedBookRepository.save(orderedBook);
            totalAmount = totalAmount.add(BigDecimal.valueOf(cart.getBook().getPrice()).multiply(BigDecimal.valueOf(cart.getCount())));

            cartRepository.delete(cart);
        }

        // Deduct amount from user's card
        for (DebitCard card : debitCards) {
            if (card.getBalance().compareTo(totalAmount) >= 0) {
                card.setBalance(card.getBalance().subtract(totalAmount));
                cardReposiotry.save(card);

                // Send email notification
                String subject = "Order Placed Notification";
                String message = "Dear " + commonUser.getName() + ",\n\n"
                        + "Your order has been successfully placed. Total amount deducted: $" + totalAmount + "\n\n"
                        + "Thank you for shopping with us.";
                emailService.sendSimpleEmail(commonUser.getUsername(), subject, message);

                break; // Exit the loop if balance deduction is successful
            }
        }

        return getUserOrders(userId);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orderList = orderRepository.findUserOrders(userId)
                .orElseThrow(() -> new ResoucreNotFoundException("userId", "userId", userId));
        return orderDto.mapToOrderResponse(orderList);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderDto.mapToOrderResponse(orderList);
    }

    public List<Status> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }

    public List<OrderResponse> updateOrderStatus(Long orderId, Long statusId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResoucreNotFoundException("orderId", "orderId", orderId));

        Status orderStatus = orderStatusRepository.findById(statusId)
                .orElseThrow(() -> new ResoucreNotFoundException("statusId", "statusId", statusId));

        order.setStatus(orderStatus);

        orderRepository.save(order);

        return getAllOrders();
    }

}
