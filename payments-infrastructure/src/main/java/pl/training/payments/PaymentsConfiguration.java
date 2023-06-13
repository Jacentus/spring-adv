package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.adapters.time.SystemTimeProvider;
import pl.training.payments.domain.*;
import pl.training.payments.ports.PaymentRepository;
import pl.training.payments.ports.PaymentService;
import pl.training.payments.ports.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public PaymentIdGenerator paymentIdGenerator() {
        return new UuidPaymentIdGenerator();
    }

    @Bean
    public PaymentFeeCalculator percentagePaymentFeeCalculator() {
        return new PercentagePaymentFeeCalculator(0.01);
    }

    @Bean
    public PaymentService paymentService(PaymentIdGenerator paymentIdGenerator, PaymentFeeCalculator paymentFeeCalculator,
                                         PaymentRepository paymentRepository, TimeProvider timeProvider) {
        return new PaymentProcessor(paymentIdGenerator, paymentFeeCalculator, paymentRepository, timeProvider);
    }

    @Bean
    public TimeProvider systemTimeProvider() {
        return new SystemTimeProvider();
    }

}
