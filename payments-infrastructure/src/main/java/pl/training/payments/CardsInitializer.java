package pl.training.payments;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.CardFactory;
import pl.training.payments.domain.CardRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class CardsInitializer {

    private final CardRepository cardRepository;

    @PostConstruct
    public void init() {
        var cardFactory = new CardFactory();
        var card = cardFactory.create("Jan Kowalski", "1234567890123456", "133", LocalDate.now().plus(1, DAYS), BigDecimal.valueOf(10_000));
        cardRepository.save(card);
    }

}
