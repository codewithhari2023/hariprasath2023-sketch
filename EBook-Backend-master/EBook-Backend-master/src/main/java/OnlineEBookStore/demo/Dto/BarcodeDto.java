package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.BarCode;
import OnlineEBookStore.demo.Model.Book;
import OnlineEBookStore.demo.Request.BookBarcodeRequest;
import OnlineEBookStore.demo.Response.BarcodeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BarcodeDto {
    public BarcodeResponse MapToBarcodeResponse(List<BarCode> barCodes)
    {
        BarcodeResponse barcodeResponse=new BarcodeResponse();
        barcodeResponse.setBarCodes(barCodes);
     return barcodeResponse;
    }
}
