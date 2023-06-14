package pl.training.payments;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import pl.training.payments.adapters.rest.RestPaymentServiceAdapter;
import pl.training.payments.adapters.websocket.WebSocketRatingSourceAdapter;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PaymentConfiguration {

    private static final String PAYMENTS = "payments";
    private static final String PROCESSED_PAYMENTS = PAYMENTS + "/processed";
    private static final String RATINGS = "ratings";

    @Bean
    public RouterFunction<ServerResponse> routes(RestPaymentServiceAdapter handler) {
        return RouterFunctions
                .route(POST(PAYMENTS).and(accept(APPLICATION_JSON)), handler::process)
                .andRoute(GET(PAYMENTS).and(accept(APPLICATION_JSON)), handler::getAllPayments)
                .andRoute(GET(PROCESSED_PAYMENTS).and(accept(APPLICATION_JSON)), handler::getProcessedPayments);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("init.sql")));
        return initializer;
    }

    @Bean
    public HandlerMapping handlerMapping(WebSocketRatingSourceAdapter handler) {
        var mapper = new SimpleUrlHandlerMapping();
        mapper.setOrder(1);
        mapper.setUrlMap(Map.of(RATINGS, handler));
        return mapper;
    }

}
