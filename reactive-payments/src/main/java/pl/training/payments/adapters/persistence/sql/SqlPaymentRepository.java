package pl.training.payments.adapters.persistence.sql;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SqlPaymentRepository extends ReactiveCrudRepository<PaymentEntity, String> {
}
