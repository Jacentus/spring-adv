package pl.training.chat;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeProvider {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss");

    public String getTime() {
        return DATE_TIME_FORMATTER.format(LocalDateTime.now());
    }

}
