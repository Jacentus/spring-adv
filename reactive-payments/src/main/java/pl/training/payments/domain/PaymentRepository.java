package pl.training.payments.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository {

    Mono<Payment> persist(Payment payment);

    Flux<Payment> getAll();

}
