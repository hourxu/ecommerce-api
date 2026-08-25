package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class CategoryHasProductsException extends BusinessException{
    public CategoryHasProductsException(){
        super(
                "cannot delete category",
                "cannot delete category",
                HttpStatus.BAD_REQUEST
        );
    }
}
