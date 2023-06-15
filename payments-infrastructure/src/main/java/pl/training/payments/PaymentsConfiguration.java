package pl.training.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pl.training.payments.adapters.output.time.remote.RestTemplateTokenInterceptor;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;
import pl.training.payments.application.input.commands.ChargeCardFeesCommandHandler;
import pl.training.payments.application.input.queries.GetCardTransactionsQueryHandler;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardRepository;

import java.util.List;

@EnableFeignClients
@EnableMongoRepositories
@EnableJpaRepositories//(repositoryImplementationPostfix = "Custom")
@EnableTransactionManagement//(order = Integer.MIN_VALUE)
@EnableAspectJAutoProxy
@Configuration
public class PaymentsConfiguration {

    @Bean
    public PaymentService paymentService(CardRepository cardRepository, CardEventsPublisher cardEventsPublisher, TimeProvider timeProvider) {
        return new PaymentService(cardRepository, cardEventsPublisher, timeProvider);
    }

    @Bean
    public ChargeCardCommandHandler chargeCardCommandHandler(PaymentService paymentService) {
        return new ChargeCardCommandHandler(paymentService);
    }

    @Bean
    public ChargeCardFeesCommandHandler chargeCardFeesCommandHandler(PaymentService paymentService) {
        return new ChargeCardFeesCommandHandler(paymentService);
    }

    @Bean
    public GetCardTransactionsQueryHandler getTransactionsQueryHandler(PaymentService paymentService) {
        return new GetCardTransactionsQueryHandler(paymentService);
    }

    @Bean
    public RestTemplate restTemplate(@Value("${tokenApi}")  String tokenApi) {
        return new RestTemplateBuilder()
                .additionalInterceptors(List.of(new RestTemplateTokenInterceptor(tokenApi)))
                .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                ///.baseUrl("http://localhost:8081")
                .build();
    }

}
