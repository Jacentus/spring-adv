package pl.training.payments.adapters.output.time;

import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.common.Adapter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Adapter
public class LocalTimeProvider implements TimeProvider {

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }

    @Override
    public ZonedDateTime getTimeStamp() {
        return ZonedDateTime.now();
    }

}
