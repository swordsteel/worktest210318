package test.work.api.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class InternalServiceException extends RuntimeException {

    public InternalServiceException(String message) {
        super(message);
    }

}
