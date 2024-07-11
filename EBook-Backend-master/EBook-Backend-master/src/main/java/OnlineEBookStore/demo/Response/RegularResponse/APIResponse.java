package OnlineEBookStore.demo.Response.RegularResponse;

import OnlineEBookStore.demo.Model.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class APIResponse {
    private Integer status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Object data;
    private Error error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(int value, String bookUpdatedSuccessfully, Book savedBook) {
    }
}
