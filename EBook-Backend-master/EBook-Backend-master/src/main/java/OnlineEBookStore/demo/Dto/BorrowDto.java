package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.Borrow;
import OnlineEBookStore.demo.Model.BorrowedBook;
import OnlineEBookStore.demo.Model.Order;
import OnlineEBookStore.demo.Request.BorrowRequest;
import OnlineEBookStore.demo.Response.BorrowResponse;
import OnlineEBookStore.demo.Response.BorrowedBookResponse;
import OnlineEBookStore.demo.Response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowDto {
    public static Borrow mapToBook(BorrowRequest borrowRequest) {
        Borrow borrow =new Borrow();
        if(borrowRequest.getId()!=null){
            borrow.setId(borrowRequest.getId());

        }
        borrow.setIssueDate(borrowRequest.getIssuedDate());
        borrow.setReturnDate(borrowRequest.getReturnDate());
        borrow.setReturned(false);
        return borrow;


    }


    public static List<BorrowResponse> mapToUserResponse(List<Borrow> userHistoryList) {

        List<BorrowResponse>  userHistoryResponseList=new ArrayList<>();

        for(Borrow userHistory:userHistoryList){
            BorrowResponse userHistoryResponse=new BorrowResponse();
            userHistoryResponse.setId(userHistory.getCommonUser().getId());
            userHistoryResponse.setName(userHistory.getCommonUser().getName());
            userHistoryResponse.setBook(userHistory.getBook().getTitle());
            userHistoryResponse.setIssuedDate(userHistory.getIssueDate());
            userHistoryResponse.setReturnDate(userHistory.getReturnDate());
            userHistoryResponse.setId(userHistory.getId());
            userHistoryResponseList.add(userHistoryResponse);

        }

        return userHistoryResponseList;
    }
}
