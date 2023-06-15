package pl.training.payments.domain;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;

@Component
public class RatingSource {

    private static final double MAX_RATING_FLUCTUACTION = 0.5;
    private static final int RATING_UPDATE_INTERVAL_IN_SECONDS = 3;

    private final Random random = new Random();

    public Flux<BigDecimal> getExchangeRates() {
        return Flux.interval(Duration.ofSeconds(RATING_UPDATE_INTERVAL_IN_SECONDS))
                .map(this::nextValue)
                .map(BigDecimal::valueOf);
    }

    private double nextValue(Long index) {
        return random.nextDouble(MAX_RATING_FLUCTUACTION) * (random.nextBoolean() ? 1 : -1);
    }

}
