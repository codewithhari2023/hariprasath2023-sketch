package com.bookshopping.bookshopping.Service;

import com.bookshopping.bookshopping.Exception.ResourceNotFoundException;
import com.bookshopping.bookshopping.Model.AppUser;
import com.bookshopping.bookshopping.Model.Book;
import com.bookshopping.bookshopping.Model.Cart;
import com.bookshopping.bookshopping.Repository.BookRepository;
import com.bookshopping.bookshopping.Repository.CartRepository;
import com.bookshopping.bookshopping.Repository.UserRepository;
import com.bookshopping.bookshopping.Request.CartRequest;
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
                .orElseThrow(() -> new ResourceNotFoundException("cart", "userId", userId));
        return cart;
    }

    @Transactional
    public List<Cart> addToCart(CartRequest cartRequest) {
        AppUser appUser = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("userId", "userId",
                        cartRequest.getUserId()));

        Book book = bookRepository.findById(cartRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("bookId", "bookId",
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
                cart.setAppUser(appUser);
                cart.setBook(book);
                cart.setCount(cartRequest.getCount());
                cartRepository.save(cart);
            }
        } else {
            Cart cart = new Cart();
            cart.setAppUser(appUser);
            cart.setBook(book);
            cart.setCount(cartRequest.getCount());
            cartRepository.save(cart);
        }
        return findUserCart(cartRequest.getUserId());
    }

    public List<Cart> deleteBookFromCart(Long userId, Long bookId) {
        return findUserCart(userId);
    }
}
