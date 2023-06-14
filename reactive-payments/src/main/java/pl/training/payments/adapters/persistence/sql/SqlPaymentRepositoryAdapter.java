package pl.training.payments.adapters.persistence.sql;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Primary
@Repository
@RequiredArgsConstructor
public class SqlPaymentRepositoryAdapter implements PaymentRepository {

    private final SqlPaymentRepository paymentRepository;
    private final SqlPaymentPersistenceMapper mapper;

    @Override
    public Mono<Payment> persist(Payment payment) {
        var paymentDocument = mapper.toEntity(payment);
        return paymentRepository.save(paymentDocument)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Payment> getAll() {
        return paymentRepository.findAll()
                .map(mapper::toDomain);
    }

}
