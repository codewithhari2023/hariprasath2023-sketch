package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Model.Messages;
import OnlineEBookStore.demo.Repository.MessageReposiotry;
import OnlineEBookStore.demo.Request.MessageRequest;
import OnlineEBookStore.demo.Service.MessageSocketServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
@Controller
public class MessageController {
    @Autowired
    private MessageReposiotry messageRepository;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageSocketServiceimpl socketService;


    @MessageMapping("/user")
    public void sendUserConversationByUserId( Long userId) {
        socketService.sendUserConversationByUserId(userId);
        logger.info("Sending conversation for user: " + userId);
    }


    @MessageMapping("/conv")
    public void sendMessagesByConversationId(Long conversationId) {
        socketService.sendMessagesByConversationId(conversationId);
    }


    @MessageMapping("/sendMessage")
    public void saveMessage( MessageRequest message) {
        socketService.saveMessage(message);
    }


    @MessageMapping("/deleteConversation")
    public void deleteConversation(Map<String, Object> payload) {
        Long conversationId = (Long) payload.get("conversationId");
        Long user1 = (Long) payload.get("user1Id");
        Long user2 = (Long) payload.get("user2Id");
        socketService.deleteConversationByConversationId(conversationId);
        socketService.sendUserConversationByUserId(user1);
        socketService.sendUserConversationByUserId(user2);
    }


    @MessageMapping("/deleteMessage")
    public void deleteMessage(Map<String, Object> payload) {
        Long conversationId = (Long) payload.get("conversationId");
        Long messageId = (Long) payload.get("messageId");
        socketService.deleteMessageByMessageId(conversationId, messageId);
    }



    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Messages sendMessage(Messages message) {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }
}

