package pl.training.payments.adapters.persistence.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoPaymentRepository extends ReactiveMongoRepository<PaymentDocument, String> {
}
