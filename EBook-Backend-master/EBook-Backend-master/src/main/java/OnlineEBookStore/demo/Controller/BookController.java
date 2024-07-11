package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Model.Roles;
import OnlineEBookStore.demo.Repository.BookRepository;
import OnlineEBookStore.demo.Request.BookRequest;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.BookService;
import OnlineEBookStore.demo.Service.StorageService;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private StorageService storageService;
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllBooks() {
        List<Book> bookList = bookService.findall();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> createbook(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            @RequestParam("price") Double price
    )throws IOException {
        String file=storageService.storeFile(photo);
        BookRequest bookRequest=new BookRequest();
        bookRequest.setPhoto(file);
        bookRequest.setAuthor(author);
        bookRequest.setDescription(description);
        bookRequest.setTitle(title);
        bookRequest.setPrice(price);
        bookRequest.setCategoryId(categoryId);
        List<Book> bookList=bookService.createbook(bookRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new  ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
    @PutMapping(value = "/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse> updateBook(
            @PathVariable Long bookId,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            @RequestParam("price") Double price
    ) {
        try {
            // Construct a Book object with the updated details
            BookRequest updatedBook = new BookRequest();
            updatedBook.setId(bookId);
            updatedBook.setCategoryId(categoryId);
            updatedBook.setTitle(title);
            updatedBook.setDescription(description);
            updatedBook.setAuthor(author);
            updatedBook.setPrice(price);

            // If a new photo is provided, set it in the updated book object
            if (photo != null && !photo.isEmpty()) {
                updatedBook.setPhoto(Arrays.toString(photo.getBytes())); // Assuming your Book entity has a byte[] field for photo
            }

            // Call the service to update the book
            Book savedBook = bookService.updateBook(updatedBook);

            // Prepare the API response
            APIResponse apiResponse = new APIResponse(HttpStatus.OK.value(), "Book updated successfully", savedBook);
            return ResponseEntity.ok(apiResponse);
        } catch (IOException e) {
            // Handle IO exception (e.g., file processing error)
            APIResponse errorResponse = new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing file", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Handle invalid input (e.g., null or invalid book ID)
            APIResponse errorResponse = new APIResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


//    @PostMapping("/download")
//    public void downloadExcel(HttpServletResponse response) {
//        List<Book> books = bookRepository.findAll(); // Assuming you have a method to fetch all books from the database
//
//        try (Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Books");
//
//            // Create header row
//            Row headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("Title");
//            headerRow.createCell(1).setCellValue("Author");
//            headerRow.createCell(2).setCellValue("Description");
//            headerRow.createCell(1).setCellValue("Price");
//
//            // Populate data rows
//            int rowNum = 1;
//            for (Book book : books) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(book.getTitle());
//                row.createCell(1).setCellValue(book.getAuthor());
//                row.createCell(2).setCellValue(book.getDescription());
//                row.createCell(3).setCellValue(book.getPrice());
//
//            }
//
//            // Set content type and header for response
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader("Content-Disposition", "attachment; filename=books.xlsx");
//
//            // Write Excel to response output stream
//            workbook.write(response.getOutputStream());
//        } catch (IOException e) {
//            // Handle exception
//            e.printStackTrace();
//            // You might want to return an error response to the client here
//        }
//
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteBook(@PathVariable Long id) {
        List<Book> bookList = bookService.deleteById(id);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(bookList);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse> searchPostsByTitle(@RequestParam("title") String title) {
        List<Book> books = bookService.findPostsByTitle(title);
        if (books.isEmpty()) {
            apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            apiResponse.setData(books);
            apiResponse.setStatus(HttpStatus.OK.value());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
