package ecommerce.project.exception;

import org.springframework.http.HttpStatus;

public class InventoryNotFoundException extends ResourceNotFoundException{
    public InventoryNotFoundException(){
        super(
                "INVENTORY_NOT_FOUND",
                "inventory not found"
        );
    }
}
