package OnlineEBookStore.demo.Exeption;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message){
        super(message);
    }
}
