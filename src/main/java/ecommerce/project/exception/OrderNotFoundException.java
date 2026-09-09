package ecommerce.project.exception;

import org.aspectj.weaver.ast.Or;

public class OrderNotFoundException extends ResourceNotFoundException{
    public OrderNotFoundException(){
        super(
                "Order not found",
                "ORDER NOT AVAILABLE"
        );
    }
}
