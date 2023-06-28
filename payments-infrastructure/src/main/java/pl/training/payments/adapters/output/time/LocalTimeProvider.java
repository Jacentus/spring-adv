package pl.training.payments.adapters.output.time;

import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.common.Adapter;

import java.time.ZonedDateTime;

@Adapter
public class LocalTimeProvider implements TimeProvider {

    @Override
    public ZonedDateTime getTimestamp() {
        return ZonedDateTime.now();
    }

}
