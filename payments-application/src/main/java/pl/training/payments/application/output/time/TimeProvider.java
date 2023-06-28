package pl.training.payments.application.output.time;

import java.time.ZonedDateTime;

public interface TimeProvider {

    ZonedDateTime getTimestamp();

}
