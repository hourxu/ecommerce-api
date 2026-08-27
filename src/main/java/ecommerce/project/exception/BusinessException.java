package ecommerce.project.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private String errorCode;
    private HttpStatus status;
    public BusinessException(String errorCode,String message,HttpStatus status){
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}
