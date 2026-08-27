package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ResourceNotFoundException{
    public UserNotFoundException() {
        super( "USER_NOT_FOUND","user not found");
    }
}
