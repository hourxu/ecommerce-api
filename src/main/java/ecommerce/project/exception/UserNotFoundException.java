package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException{
    public UserNotFoundException() {
        super("user not found", "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
