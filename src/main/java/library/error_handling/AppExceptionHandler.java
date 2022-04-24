package library.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceNotFoundException;
import javax.security.auth.login.LoginException;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentExceptionHandler(IllegalArgumentException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> IllegalStateExceptionHandler(IllegalStateException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<Object> InstanceNotFoundExceptionHandler(InstanceNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> LoginExceptionHandler(LoginException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> NumberFormatExceptionHandler(NumberFormatException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
