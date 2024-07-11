package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Model.CommonUser;

import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Request.ChatMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

//    @Autowired
//    private MessageReposiotry messageRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private Map<Long, List<ChatMessage>> chatHistory = new HashMap<>();
//    private Long messageId = 1L; // To generate unique message IDs
//
//    public void sendMessage(ChatMessage message) {
//        message.setId(messageId++); // Assign a unique ID to the message
//        message.setTimestamp(LocalDateTime.now());
//
//        Long userId = message.getUserId(); // Assuming userId is available in the message
//        if (!chatHistory.containsKey(userId)) {
//            chatHistory.put(userId, new ArrayList<>());
//        }
//        chatHistory.get(userId).add(message);
//    }
//
//    public List<ChatMessage> getChatHistory(Long userId) {
//        return chatHistory.getOrDefault(userId, new ArrayList<>());

    // Other methods for managing friend lists, handling friend requests, etc.

}
