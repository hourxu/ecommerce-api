package ecommerce.project.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ProductAlreadyExistedException extends BusinessException{
    public ProductAlreadyExistedException(){
        super(
                "PRODUCT ALREADY EXISTED",
                "product name already existed",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
