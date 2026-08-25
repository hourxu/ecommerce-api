package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class DeleteSuccessException extends BusinessException{
    public DeleteSuccessException(){
        super(
                "Deleted success ",
                "Deleted success",
                HttpStatus.OK

        );
    }
}
