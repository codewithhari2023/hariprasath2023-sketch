package OnlineEBookStore.demo.Exeption;

import lombok.Getter;

@Getter
public class ResoucreNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResoucreNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
