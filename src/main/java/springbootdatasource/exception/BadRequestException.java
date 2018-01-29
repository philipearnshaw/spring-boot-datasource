package springbootdatasource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {}
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
