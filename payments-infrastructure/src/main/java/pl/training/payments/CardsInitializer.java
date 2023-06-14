package pl.training.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.domain.CardFactory;
import pl.training.payments.domain.CardRepository;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Transactional
//@Component
@RequiredArgsConstructor
public class CardsInitializer implements ApplicationRunner {

    private final CardFactory cardFactory = new CardFactory();
    private final CardRepository cardRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var expirationDate =  LocalDate.now().plus(10, DAYS);
        var card = cardFactory.create("1234567890123456", expirationDate, 10_000);
        cardRepository.save(card);
    }

}
