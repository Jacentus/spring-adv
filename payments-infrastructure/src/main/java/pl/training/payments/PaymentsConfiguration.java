package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;
import pl.training.payments.application.input.queries.GetTransactionsQueryHandler;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardRepository;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public PaymentService paymentService(CardRepository cardRepository, TimeProvider timeProvider, CardEventsPublisher cardEventsPublisher) {
        return new PaymentService(cardRepository, timeProvider, cardEventsPublisher);
    }

    @Bean
    public ChargeCardCommandHandler chargeCardCommandHandler(PaymentService paymentService) {
        return new ChargeCardCommandHandler(paymentService);
    }

    @Bean
    public GetTransactionsQueryHandler getTransactionsQueryHandler(PaymentService paymentService) {
        return new GetTransactionsQueryHandler(paymentService);
    }

}
