package ecommerce.project.exception;

public class ImageNotFound extends ResourceNotFoundException{
    public ImageNotFound(){
        super("Image not Found","Image not found");
    }
}
