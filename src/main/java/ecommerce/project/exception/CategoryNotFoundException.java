package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BusinessException{
    public CategoryNotFoundException(){
        super(
                "CATEGORY NOT FOUND",
                "Provided other name",
                HttpStatus.NOT_FOUND
        );
    }
}
