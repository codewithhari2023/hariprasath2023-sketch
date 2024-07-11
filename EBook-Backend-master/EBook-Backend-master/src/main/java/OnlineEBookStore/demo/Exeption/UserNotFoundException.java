package OnlineEBookStore.demo.Exeption;

public class UserNotFoundException extends  RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
