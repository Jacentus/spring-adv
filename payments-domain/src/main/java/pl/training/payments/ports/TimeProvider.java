package pl.training.payments.ports;

import java.time.Instant;

public interface TimeProvider {

    Instant getTimestamp();

}
