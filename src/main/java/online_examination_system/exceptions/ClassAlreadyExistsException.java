package online_examination_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClassAlreadyExistsException extends RuntimeException {
    public ClassAlreadyExistsException(String message) {
        super(message);
    }
}
