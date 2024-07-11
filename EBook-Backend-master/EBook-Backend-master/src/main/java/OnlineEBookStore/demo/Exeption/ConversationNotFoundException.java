package OnlineEBookStore.demo.Exeption;

public class ConversationNotFoundException extends  RuntimeException {
    public ConversationNotFoundException(String message) {
        super(message);
    }
}
