package book.project.First.exceptions.handler;

import book.project.First.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorResponse(int status, String error, String message, Instant timestamp) {
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(BaseException e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus httpStatus = responseStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : responseStatus.value();

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), httpStatus.name(), e.getMessage(), Instant.now());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}