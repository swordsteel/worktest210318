package test.work.api.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class FoundException extends RuntimeException {

    public FoundException(String message) {
        super(message);
    }

}
