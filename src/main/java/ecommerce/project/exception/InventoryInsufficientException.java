package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class InventoryInsufficientException extends  BusinessException {
    public InventoryInsufficientException() {
        super(
                "INVENTORY_INSUFFICIENT",
                "product quantity not enough",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
