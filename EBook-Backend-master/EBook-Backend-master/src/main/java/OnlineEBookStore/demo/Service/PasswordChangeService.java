package OnlineEBookStore.demo.Service;

import OnlineEBookStore.demo.Model.CommonUser;
import OnlineEBookStore.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordChangeService {
    @Autowired
    private UserRepository userRepository;

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        CommonUser user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true; // Password changed successfully
        }

        return false; // User not found or current password is incorrect
    }
}
