package ecommerce.project.exception;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException() {
        super(
                "CART_NOT_FOUND",
                "cart not found"
        );
    }
}
