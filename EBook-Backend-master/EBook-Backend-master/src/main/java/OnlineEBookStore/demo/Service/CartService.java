package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.Cart;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Repository.BookRepository;
import OnlineEBookStore.demo.Repository.CartRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Cart> findUserCart(Long userId) {
        List<Cart> cart = cartRepository.findUserCart(userId)
                .orElseThrow(() -> new ResoucreNotFoundException("cart", "userId", userId));

        return cart;
    }

    @Transactional
    public List<Cart> addToCart(CartRequest cartRequest) {
        CommonUser commonUser = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new ResoucreNotFoundException("userId", "userId",
                        cartRequest.getUserId()));

        Book book = bookRepository.findById(cartRequest.getBookId())
                .orElseThrow(() -> new ResoucreNotFoundException("bookId", "bookId",
                        cartRequest.getBookId()));

        Optional<List<Cart>> cartOptional = cartRepository.findUserCart(cartRequest.getUserId());

        if (cartOptional.isPresent()) {
            boolean isPresent = false;
            for (Cart cart : cartOptional.get()) {
                if (cart.getBook().getId().equals(cartRequest.getBookId())) {
                    cart.setCount(cartRequest.getCount());
                    cartRepository.save(cart);
                    isPresent = true;
                }
            }
            if (!isPresent) {
                Cart cart = new Cart();
                cart.setCommonUser(commonUser);
                cart.setBook(book);
                cart.setCount(cartRequest.getCount());
                cartRepository.save(cart);
            }
        } else {
            Cart cart = new Cart();
            cart.setCommonUser(commonUser);
            cart.setBook(book);
            cart.setCount(cartRequest.getCount());
            cartRepository.save(cart);
        }
        return findUserCart(cartRequest.getUserId());
    }

    @Transactional
    public List<Cart> deleteBookFromCart(Long userId, Long bookId) {
        cartRepository.deleteCart(bookId,userId);
        return findUserCart(userId);
    }
}
