package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Model.Conversation;
import OnlineEBookStore.demo.Repository.ConversationRepository;
import OnlineEBookStore.demo.Repository.UserRepository;
import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserServiceimpl {
    @Autowired
    private  APIResponse apiResponse;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public ResponseEntity<APIResponse> findAllUsersExceptThisUserId(Long userId) {
        List<CommonUser> list = userRepository.findAllUsersExceptThisUserId(userId);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(list);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    /**
     * Find or create a conversation ID for a pair of users based on their user IDs.
     *
     * @param user1Id The ID of the first user in the conversation.
     * @param user2Id The ID of the second user in the conversation.
     * @return ResponseEntity containing an ApiResponse with the conversation ID for the user pair.
     */

    public ResponseEntity<APIResponse> findConversationIdByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        Long conversationId;
        Optional<CommonUser> user1 = userRepository.findById(user1Id);
        Optional<CommonUser> user2 = userRepository.findById(user2Id);
        if (user1.isEmpty() || user2.isEmpty()) {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(null);
            return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }

        Optional<Conversation> existingConversation = conversationRepository.findConversationByUsers(user1.get(), user2.get());
        if (existingConversation.isPresent()) {
            conversationId = existingConversation.get().getConversationId();
        } else {
            Conversation newConversation = new Conversation();
            newConversation.setUser1(user1.get());
            newConversation.setUser2(user2.get());
            Conversation savedConversation = conversationRepository.save(newConversation);
            conversationId = savedConversation.getConversationId();
        }
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(conversationId);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
