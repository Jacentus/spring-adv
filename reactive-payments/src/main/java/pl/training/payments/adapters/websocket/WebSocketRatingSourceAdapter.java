package pl.training.payments.adapters.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import pl.training.payments.domain.RatingSource;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Log
@RequiredArgsConstructor
public class WebSocketRatingSourceAdapter implements WebSocketHandler {

    private final RatingSource ratingSource;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var messages = ratingSource.getExchangeRates()
                .map(BigDecimal::toString)
                .map(session::textMessage);
        return session.send(messages)
                .and(session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(this::onMessage));

    }

    private void onMessage(String message) {
        log.info("New message: " + message);
    }

}
