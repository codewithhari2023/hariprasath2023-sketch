package OnlineEBookStore.demo.Service;


import OnlineEBookStore.demo.Exeption.ResoucreNotFoundException;
import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Conversation;
import OnlineEBookStore.demo.Model.Messages;
import OnlineEBookStore.demo.Repository.ConversationRepository;
import OnlineEBookStore.demo.Repository.MessageReposiotry;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.MessageRequest;
import OnlineEBookStore.demo.Response.ConversationResponse;
import OnlineEBookStore.demo.Response.MessageResponse;
import OnlineEBookStore.demo.Response.WebSocketResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageSocketServiceimpl implements MessageSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageReposiotry messageRepository;
    private static final Logger logger = LoggerFactory.getLogger(MessageSocketServiceimpl.class);

    /**
     * Send user conversations to a specific user by their user ID through a web socket.
     *
     * @param userId The ID of the user for whom to send conversations.
     */

    public void sendUserConversationByUserId(Long userId) {
        List<ConversationResponse> conversation = conversationRepository.findConversationsByUserId(userId);
        messagingTemplate.convertAndSend(
                "/topic/user/".concat(String.valueOf(userId)),
                WebSocketResponse.builder()
                        .type("ALL")
                        .data(conversation)
                        .build()
        );
    }

    /**
     * Send messages of a specific conversation to the connected users through a web socket.
     *
     * @param conversationId The ID of the conversation for which to send messages.
     */
    public void sendMessagesByConversationId(Long conversationId) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        List<Messages> messageList = messageRepository.findAllByConversation(conversation);
        List<MessageResponse> messageResponseList = messageList.stream()
                .map((message -> MessageResponse.builder()
                        .messageId(message.getMessageId())
                        .message(message.getMessage())
                        .timestamp(Date.from(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                        .senderId(message.getSender().getId())
                        .receiverId(message.getReceiver().getId())
                        .build())
                ).toList();
        messagingTemplate.convertAndSend("/topic/conv/".concat(String.valueOf(conversationId)), WebSocketResponse.builder()
                .type("ALL")
                .data(messageResponseList)
                .build()
        );
    }
    @Override
    public void saveMessage(MessageRequest msg) {
        CommonUser sender = userRepository.findById(msg.getSenderId()).get();
        System.out.println("sender");
        CommonUser receiver = userRepository.findById(msg.getReceiverId()).get();
        Conversation conversation = conversationRepository.findConversationByUsers(sender, receiver).get();
        Messages newMessage = new Messages();
        newMessage.setMessage(msg.getMessage());
        newMessage.setTimestamp(msg.getTimestamp());
        newMessage.setConversation(conversation);
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        Messages savedMessage = messageRepository.save(newMessage);
        // notify listener
        MessageResponse res = MessageResponse.builder()
                .messageId(savedMessage.getMessageId())
                .message(savedMessage.getMessage())
                .timestamp(Date.from(savedMessage.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()))
                .senderId(savedMessage.getSender().getId())
                .receiverId(savedMessage.getReceiver().getId())
                .build();
        messagingTemplate.convertAndSend("/topic/conv/".concat(msg.getConversationId().toString()),
                WebSocketResponse.builder()
                        .type("ADDED")
                        .data(res)
                        .build()
        );
        sendUserConversationByUserId(msg.getSenderId());
        sendUserConversationByUserId(msg.getReceiverId());
    }

    /**
     * Save a new message using a web socket.
     *
     * @param msg The MessageRequest object containing the message details to be saved.
     */


    /**
     * Delete a conversation by its unique conversation ID using a web socket.
     *
     * @param conversationId The ID of the conversation to be deleted.
     */
    @Transactional
    public void deleteConversationByConversationId(Long conversationId) {
        Conversation c = new Conversation();
        c.setConversationId(conversationId);
        messageRepository.deleteAllByConversation(c);
        conversationRepository.deleteById(conversationId);
    }

    /**
     * Delete a message by its unique message ID within a conversation using a web socket.
     *
     * @param conversationId The ID of the conversation to notify its listener.
     * @param messageId      The ID of the message to be deleted.
     */
    public void deleteMessageByMessageId(Long conversationId, Long messageId) {
        messageRepository.deleteById(messageId);
        // notify listener
        sendMessagesByConversationId(conversationId);
    }
}
