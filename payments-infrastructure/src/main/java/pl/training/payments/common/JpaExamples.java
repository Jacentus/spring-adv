package pl.training.payments.common;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import pl.training.payments.adapters.output.persistence.jpa.CardEntity;
import pl.training.payments.adapters.output.persistence.jpa.JpaCardRepository;
import pl.training.payments.domain.CardFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Transactional
@Component
@RequiredArgsConstructor
@Log
public class JpaExamples implements ApplicationRunner {

    private final JpaCardRepository cardRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*cardRepository.getWithValue(BigDecimal.TEN, PageRequest.of(0, 10))
                .forEach(cardEntity -> log.info(cardEntity.getNumber()));*/

        /*cardRepository.getAllWithValue(BigDecimal.ONE)
                .forEach(view -> log.info(view.toString()));*/

        /*cardRepository.getAllWithValueAsStream(BigDecimal.ZERO)
                .forEach(view -> log.info(view.getNumber() + ": " + view.getBalance()));*/

        /*var result = cardRepository.getAllAsync();
        log.info("Is done: " +  result.isDone());
        log.info("Rows:" +  result.get().size());*/

        //log.info("Cards with zero balance: " +

        var exampleCardEntity = new CardEntity();
        exampleCardEntity.setBalance(BigDecimal.valueOf(10_000));
        var matcher = ExampleMatcher.matching()
                .withIgnorePaths("version", "transactions")
                .withIgnoreCase()
                .withIgnoreNullValues();
        var example = Example.of(exampleCardEntity, matcher);
        cardRepository.findAll(example)
              .forEach(view -> log.info(view.getNumber() + ": " + view.getBalance()));
    }

}
