package pl.training.payments.adapters.time;

import pl.training.payments.ports.TimeProvider;

import java.time.Instant;

public class SystemTimeProvider implements TimeProvider {

    @Override
    public Instant getTimestamp() {
        return Instant.now();
    }

}
