package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class InventoryNotFoundException extends BusinessException{
    public InventoryNotFoundException(){
        super(
                "Inventory not found",
                "Inventory not found",
                HttpStatus.NOT_FOUND
        );
    }
}
