package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Request.CardRequest;
import OnlineEBookStore.demo.Response.CardResponse;
import org.springframework.stereotype.Component;

@Component
public class CardDto {
    public CardResponse mapTocard(CardRequest cardRequest){
        CardResponse cardResponse=new CardResponse();

        CommonUser commonUser=new CommonUser();

        cardResponse.setCardNumber(cardRequest.getCardNumber());
        System.out.println(cardRequest.getCardNumber());
        cardResponse.setId(cardRequest.getId());
        cardResponse.setCvv(cardRequest.getCvv());
        cardResponse.setExpirydate((cardRequest.getExpirydate()));
        cardResponse.setBalance(cardRequest.getBalance());
        cardResponse.setUserId(cardRequest.getUserId());
        return cardResponse;
    }
}
