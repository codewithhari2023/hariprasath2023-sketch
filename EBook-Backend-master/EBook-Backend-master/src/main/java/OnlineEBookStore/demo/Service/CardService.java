package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Dto.CardDto;
import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.DebitCard;
import OnlineEBookStore.demo.Repository.CardReposiotry;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.CardRequest;
import OnlineEBookStore.demo.Response.CardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardReposiotry cardReposiotry;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardDto cardDto;

    public CardResponse saveCard(CardRequest cardRequest)
    {
        CommonUser commonUser=userRepository.findById(cardRequest.getUserId()).orElseThrow(()->new ResoucreNotFoundException("userid","userId",cardRequest.getUserId()));
        DebitCard debitCard=new DebitCard();
        debitCard.setCardNumber(cardRequest.getCardNumber());
        debitCard.setId(cardRequest.getId());
        debitCard.setBalance(BigDecimal.valueOf(cardRequest.getBalance()));
        debitCard.setCvv((cardRequest.getCvv()));
        debitCard.setExpirydate(cardRequest.getExpirydate());
        debitCard.setCommonUser(commonUser);
        cardReposiotry.save(debitCard);
        return cardDto.mapTocard(cardRequest);
    }
    public Optional<List<DebitCard>> getCard(Long userId)
    {
        Optional<List<DebitCard>> card=cardReposiotry.findUserCards(userId);

       return  card;
    }
    public List<DebitCard> getAllCard()
    {
        List<DebitCard> Card=cardReposiotry.findAll();

        return  Card;
    }

}
