package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ResourceNotFoundException{
    public ProductNotFoundException(){
        super(
                "PRODUCT_NOT_FOUND",
                "product not found"
        );
    }
}
