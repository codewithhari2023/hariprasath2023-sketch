package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Borrow;
import OnlineEBookStore.demo.Request.PenaltyRequest;
import OnlineEBookStore.demo.Service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
@RestController
@RequestMapping("/api/penalty")
public class PenaltyController {
    @Autowired
    private PenaltyService penaltyService;
    @PostMapping("/amount")
    public ResponseEntity<BigDecimal> calculatePenaltyAmount(@RequestBody PenaltyRequest borrow) {
        BigDecimal penaltyAmount = penaltyService.calculatePenaltyAmount(borrow);
        return ResponseEntity.ok(penaltyAmount);
    }
}
