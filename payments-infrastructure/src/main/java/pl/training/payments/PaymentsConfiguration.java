package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;
import pl.training.payments.application.input.commands.ChargeCardFeesCommandHandler;
import pl.training.payments.application.input.queries.GetCardTransactionsQueryHandler;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardRepository;

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

}
