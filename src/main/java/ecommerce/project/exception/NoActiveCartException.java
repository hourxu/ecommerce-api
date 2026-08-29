package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class NoActiveCartException extends BusinessException{
    public NoActiveCartException() {
        super(
                "NO_ACTIVE_CART",
                "No Active cart available",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
