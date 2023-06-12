package pl.training.payments.commons.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.training.payments.ports.TimeProvider;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class RestExceptionResponseBuilder {

    private final MessageSource messageSource;
    private final TimeProvider timeProvider;

    public ResponseEntity<ExceptionDto> build(String description, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ExceptionDto(timeProvider.getTimestamp(), description));
    }

    public ResponseEntity<ExceptionDto> build(Exception exception, HttpStatus status, Locale locale) {
        return ResponseEntity.status(status)
                .body(new ExceptionDto(timeProvider.getTimestamp(), getDescription(exception, locale)));
    }

    public String getDescription(Exception exception, Locale locale, String...parameters) {
        var key = toKey(exception);
        String description;
        try {
            description = messageSource.getMessage(key, parameters, locale);
        } catch (NoSuchMessageException noSuchMessageException) {
            description = key;
        }
        return description;
    }

    private String toKey(Exception exception) {
        return exception.getClass().getSimpleName();
    }

}
