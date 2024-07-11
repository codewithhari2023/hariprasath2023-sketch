package OnlineEBookStore.demo.Controller;

import OnlineEBookStore.demo.Response.RegularResponse.APIResponse;
import OnlineEBookStore.demo.Service.UserServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserconvoController {
    @Autowired
    private  UserServiceimpl userServiceimpl;
    @GetMapping("/except/{userId}")
    public ResponseEntity<APIResponse> findAllUsersExceptThisUserId(@PathVariable Long userId) {
        return userServiceimpl.findAllUsersExceptThisUserId(userId);

    }

    /**
     * Find or create a conversation ID for a pair of users based on their user IDs.
     *
     * @param user1Id The ID of the first user in the conversation.
     * @param user2Id The ID of the second user in the conversation.
     * @return ResponseEntity containing an ApiResponse with the conversation ID for the user pair.
     */
    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<APIResponse> findConversationIdByUser1IdAndUser2Id(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        return userServiceimpl.findConversationIdByUser1IdAndUser2Id(user1Id, user2Id);
    }
}
