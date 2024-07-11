package OnlineEBookStore.demo.Request;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookRequest {
    private Long id;
    private Long categoryId;
    private String title;
    private String author;
    private Double price;
    private String description;
    private String photo;
}
