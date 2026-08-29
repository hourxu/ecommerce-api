package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BusinessException{
    public UserAlreadyExistException(){
        super(
                "USER_ALREADY_EXIST",
                "the provided user already exists",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
