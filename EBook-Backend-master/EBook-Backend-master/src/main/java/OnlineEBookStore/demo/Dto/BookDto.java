package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Request.BookRequest;
import OnlineEBookStore.demo.Response.BookResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDto {
    public BookResponse mapToBookResponse(List<Book> bookList) {
        return new BookResponse();
    }
    public Book mapToBook(BookRequest bookRequest) {
        Book book = new Book();
        if (bookRequest.getId() != null) {
            book.setId(bookRequest.getId());
        }
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setDescription(bookRequest.getDescription());
        book.setTitle(bookRequest.getTitle());
        book.setPhoto(bookRequest.getPhoto());
        return book;
    }
}
