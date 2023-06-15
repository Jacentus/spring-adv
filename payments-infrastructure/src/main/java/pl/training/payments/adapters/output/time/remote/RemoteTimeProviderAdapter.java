package pl.training.payments.adapters.output.time.remote;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClientException;
import pl.training.payments.application.annotations.Retry;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.common.Adapter;

import java.time.ZonedDateTime;

@Primary
@Adapter
@RequiredArgsConstructor
public class RemoteTimeProviderAdapter implements TimeProvider {

    //private final RestTemplateProvider timeProvider;
    private final FeignDateTimeProvider timeProvider;
    private final RestDateTimeMapper mapper;

    @Retry
    @Override
    public ZonedDateTime getTimeStamp() {
        try {
            return timeProvider.getDateTime()
                    .map(mapper::toDomain)
                    .orElseThrow(ServiceUnavailableException::new);
        } catch (RestClientException restClientException) {
            throw new ServiceUnavailableException();
        }
    }

}
