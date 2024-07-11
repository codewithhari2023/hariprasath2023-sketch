package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.BookDto;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.Category;
import OnlineEBookStore.demo.Repository.BookRepository;
import OnlineEBookStore.demo.Repository.CategoryRepository;
import OnlineEBookStore.demo.Request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private BookDto bookDto;
    public List<Book> findall() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public Book updateBook(BookRequest book) {
        // Check if the provided book object is null
        if (book == null) {
            throw new IllegalArgumentException("Book object cannot be null");
        }

        // Check if the book ID is not null and exists in the database
        Long bookId = book.getId();
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        // Perform any necessary validations or business logic
        // For example, you may want to restrict certain fields from being updated
        // based on business rules or user permissions

        // Update the fields that are allowed to be updated
        // For example, update only if the new value is not null
        String newTitle = book.getTitle();
        if (newTitle != null) {
            existingBook.setTitle(newTitle);
        }

        String newDescription = book.getDescription();
        if (newDescription != null) {
            existingBook.setDescription(newDescription);
        }

        String newAuthor = book.getAuthor();
        if (newAuthor != null) {
            existingBook.setAuthor(newAuthor);
        }

        Double newPrice = book.getPrice();
        if (newPrice != null) {
            existingBook.setPrice(newPrice);
        }

        // Save the updated book
        return bookRepository.save(existingBook);
    }
    @Transactional
    public List<Book> createbook(BookRequest bookRequest) {
        Book book = bookDto.mapToBook(bookRequest);
        Category category = categoryRepository.findById(bookRequest.getCategoryId()).orElseThrow(() -> new ResoucreNotFoundException("CategoryId", "CategoryId", bookRequest.getCategoryId()));
        book.setCategory(category);
        bookRepository.save(book);
        return findall();
    }
    public File getbook(Long id) throws IOException {
        Book Book = bookRepository.findById(id)
                .orElseThrow(() -> new ResoucreNotFoundException("id", "id", id));

        Resource resource = storageService.loadFileAsResource(Book.getPhoto());

        return resource.getFile();
    }

    public void saveBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }
    public List<Book> deleteById(Long id) {
        bookRepository.deleteById(id);
        return findall();
    }
    // Search for Post
    public List<Book> findPostsByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
