package pl.training.payments.adapters.output.stream;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component
@Log
@RequiredArgsConstructor
public class PaymentsConsumer implements ApplicationRunner {

    private final WebClient webClient;

    @Value("${processedPaymentsApi}")
    @Setter
    private URI processedPaymentsApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webClient.get()
                .uri(processedPaymentsApi)
                .retrieve()
                .bodyToFlux(PaymentDto.class)
                .subscribe(paymentDto -> log.info("Payment processed: " + paymentDto), throwable -> log.warning(throwable.toString()));
    }

}
