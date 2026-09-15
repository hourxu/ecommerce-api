package ecommerce.project.exception;

public class WallNotFoundException extends ResourceNotFoundException{
    public WallNotFoundException(){
        super("Wallet not found ","Wallet not found");
    }
}
