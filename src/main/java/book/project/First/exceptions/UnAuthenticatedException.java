package book.project.First.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthenticatedException extends BaseException {

    public UnAuthenticatedException(String message) {
        super(message);
    }
}
