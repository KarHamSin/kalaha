package bol.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(GameException.class)
    private ResponseEntity handleException(GameException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}