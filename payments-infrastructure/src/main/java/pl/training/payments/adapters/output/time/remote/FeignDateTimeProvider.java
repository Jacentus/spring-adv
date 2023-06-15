package pl.training.payments.adapters.output.time.remote;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeignDateTimeProvider {

    private final DateTimeApi dateTimeApi;

    public Optional<DateTimeDto> getDateTime() {
        return  Optional.ofNullable(dateTimeApi.getDateTime());
    }

}
