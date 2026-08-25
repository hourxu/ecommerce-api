package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistedException extends BusinessException{
    public CategoryAlreadyExistedException() {
        super(
                "CATEGORY_ALREARY_EXIST",
                "category with the provided name already exist",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
