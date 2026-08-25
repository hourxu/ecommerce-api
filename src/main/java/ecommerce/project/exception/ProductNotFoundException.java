package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BusinessException{
    public ProductNotFoundException(){
        super(
                "Product not found",
                "Product not found",
                HttpStatus.NOT_FOUND
        );
    }
}
