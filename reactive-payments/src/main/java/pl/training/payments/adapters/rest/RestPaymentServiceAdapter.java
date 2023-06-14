package pl.training.payments.adapters.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.training.payments.domain.PaymentService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestPaymentServiceAdapter {

    private final PaymentService paymentService;
    private final PaymentRestMapper mapper;

    public Mono<ServerResponse> process(ServerRequest serverRequest) {
        var paymentMono = serverRequest.bodyToMono(PaymentDto.class).map(mapper::toDomain);
        var paymentDtoMono = paymentService.process(paymentMono).map(mapper::toDto);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentDtoMono, PaymentDto.class);
    }

    public Mono<ServerResponse> getAllPayments(ServerRequest serverRequest) {
        var paymentFlux = paymentService.getAllPayments().map(mapper::toDto);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentFlux, PaymentDto.class);
    }

    public Mono<ServerResponse> getProcessedPayments(ServerRequest serverRequest) {
        var paymentFlux = paymentService.getProcessedPayments().map(mapper::toDto);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentFlux, PaymentDto.class);
    }

}
