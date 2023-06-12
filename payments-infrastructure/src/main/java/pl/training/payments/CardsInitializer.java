package pl.training.payments;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.CardFactory;
import pl.training.payments.domain.CardRepository;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class CardsInitializer {

    private final CardFactory cardFactory = new CardFactory();
    private final CardRepository cardRepository;

    @PostConstruct
    public void init() {
        var expirationDate =  LocalDate.now().plus(1, DAYS);
        var card = cardFactory.create("Jan Kowalski", "1234567890123456", "133", expirationDate, 10_000);
        cardRepository.save(card);
    }

}
