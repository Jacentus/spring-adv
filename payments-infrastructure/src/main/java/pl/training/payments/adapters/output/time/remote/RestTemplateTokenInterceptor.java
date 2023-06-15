package pl.training.payments.adapters.output.time.remote;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Log
public class RestTemplateTokenInterceptor implements ClientHttpRequestInterceptor {

    private static final String TOKEN_PREFIX = "bearer ";

    private final String token;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set(AUTHORIZATION, TOKEN_PREFIX + token);
        log.info("Headers: " + request.getHeaders());
        return execution.execute(request, body);
    }

}
