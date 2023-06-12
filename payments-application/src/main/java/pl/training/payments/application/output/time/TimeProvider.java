package pl.training.payments.application.output.time;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface TimeProvider {

    LocalDate getDate();

    ZonedDateTime getTimeStamp();

}
