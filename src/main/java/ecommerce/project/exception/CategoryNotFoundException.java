package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends ResourceNotFoundException{
    public CategoryNotFoundException(){
        super(
                "CATEGORY_NOT_FOUND",
                "cannot find the category"
        );
    }
}
