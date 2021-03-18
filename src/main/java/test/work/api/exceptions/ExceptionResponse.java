package test.work.api.exceptions;

import lombok.Getter;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
public class ExceptionResponse {

    private final LocalDateTime timestamp;
    private final String message;
    private final String path;
    private Collection<String> constraintViolations;

    ExceptionResponse(String message, WebRequest request) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.path = request.getDescription(false);
    }

    ExceptionResponse(WebRequest request, Collection<String> constraintViolations) {
        this("Please check constraints violations.", request);
        this.constraintViolations = constraintViolations;
    }

}