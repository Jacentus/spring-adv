package pl.training.payments.adapters.output.time.remote;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestTemplateDateTimeProvider {

    private final RestTemplate restTemplate;

    @Value("${dateTimeApi}")
    @Setter
    private URI dateTimeApi;

    public Optional<DateTimeDto> getDateTime() {
        return Optional.ofNullable(restTemplate.getForObject(dateTimeApi, DateTimeDto.class));
    }

}
