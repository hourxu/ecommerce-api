package ecommerce.project.exception;

public class ProfileNotFoundException extends ResourceNotFoundException{
    public ProfileNotFoundException(){
        super(
                "PROFILE NOT FOUND",
                "not found"
        );
    }
}
