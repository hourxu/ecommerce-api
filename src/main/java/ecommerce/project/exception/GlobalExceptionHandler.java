package ecommerce.project.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e){
        return new ResponseEntity<>(
                Map.of(
                        "errorCode",e.getErrorCode(),
                        "message",e.getMessage(),
                        "status", e.getStatus().value()
                ),
                e.getStatus()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e){
        List<String> message =  e.getBindingResult().getFieldErrors().stream().map(f->f.getDefaultMessage()).toList();
        return new ResponseEntity<>(
                Map.of(
                        "message", message,
                        "status", HttpStatus.BAD_REQUEST.value()
                ),
                HttpStatus.BAD_REQUEST

        );
    }
}
