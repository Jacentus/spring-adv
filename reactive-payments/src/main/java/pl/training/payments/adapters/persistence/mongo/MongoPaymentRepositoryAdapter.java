package pl.training.payments.adapters.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoPaymentRepositoryAdapter implements PaymentRepository {

    private final MongoPaymentRepository paymentRepository;
    private final MongoPaymentPersistenceMapper mapper;

    @Override
    public Mono<Payment> persist(Payment payment) {
        var paymentDocument = mapper.toDocument(payment);
        return paymentRepository.save(paymentDocument)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Payment> getAll() {
        return paymentRepository.findAll()
                .map(mapper::toDomain);
    }

}
