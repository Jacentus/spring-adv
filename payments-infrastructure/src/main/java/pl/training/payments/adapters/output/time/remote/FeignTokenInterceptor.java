package pl.training.payments.adapters.output.time.remote;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    private static final String TOKEN_PREFIX = "bearer ";

    @Value("${tokenApi}")
    @Setter
    private String token;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(AUTHORIZATION, TOKEN_PREFIX + token);
    }

}
