package pl.training.payments.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final Sinks.Many<Payment> paymentSink= Sinks.many().replay().all(3);

    public Flux<Payment> getAllPayments() {
        return paymentRepository.getAll();
    }

    public Flux<Payment> getProcessedPayments() {
        return paymentSink.asFlux();
    }

    public Mono<Payment> process(Mono<Payment> paymentMono) {
        return paymentMono.map(Payment::confirmed)
                .flatMap(paymentRepository::persist)
                .doOnNext(paymentSink::tryEmitNext);
    }

}
