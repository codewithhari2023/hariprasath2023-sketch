package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Dto.CardDto;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.DebitCard;
import OnlineEBookStore.demo.Request.CardRequest;
import OnlineEBookStore.demo.Request.ExcelRequest;
import OnlineEBookStore.demo.Response.CardResponse;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.CardService;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private APIResponse apiResponse;
    @Autowired
    private CardDto cardDto;
    @PostMapping("/add")
    public ResponseEntity<APIResponse> CreateCard(@RequestBody CardRequest cardRequest) throws IOException {
      CardResponse cardResponse = cardService.saveCard(cardRequest);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(cardResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/usercard/{userId}")
    public ResponseEntity<APIResponse> getCard(@PathVariable Long userId) throws IOException {
        Optional<List<DebitCard>> Card = cardService.getCard(userId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(Card);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCard() throws IOException {
        List<DebitCard> Card = cardService.getAllCard();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(Card);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
