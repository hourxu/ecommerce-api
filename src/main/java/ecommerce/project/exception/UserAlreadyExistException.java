package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BusinessException{
    public UserAlreadyExistException(){
        super(
                "name already exist",
                "Enter another name ",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
