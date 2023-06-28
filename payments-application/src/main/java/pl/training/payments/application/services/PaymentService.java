package pl.training.payments.application.services;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.output.events.CardChargedApplicationEvent;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.domain.*;

import java.util.List;
import java.util.function.Consumer;

import static pl.training.payments.domain.CardTransactionType.FEE;
import static pl.training.payments.domain.CardTransactionType.WITHDRAW;

// w przyszłości trzeba będzie rozdzielić tą klasę na wiele serwiswów/use casów
@RequiredArgsConstructor
public class PaymentService {

    private final CardRepository cardRepository;
    private final CardEventsPublisher cardEventsPublisher;
    private final TimeProvider timeProvider;

    public void chargeCard(CardNumber number, Money amount) {
        processOperation(number, card -> {
            card.addEventsListener(createCardChargedEventListener());
            return new CardTransaction(timeProvider.getTimestamp(), amount, WITHDRAW);
        });
    }

    public List<CardTransaction> getTransactions(CardNumber number) {
        return getCard(number).getTransactions();
    }

    public void chargeFees(CardNumber number) {
        processOperation(number, card -> {
            var fees = new CardTransactionBasedFees(card.getTransactions())
                    .execute();
            return new CardTransaction(timeProvider.getTimestamp(), fees, FEE);
        });
    }

    private void processOperation(CardNumber number, Operation operation) {
        var card = getCard(number);
        var transaction = operation.execute(card);
        card.addTransaction(transaction);
        cardRepository.save(card);
    }

    private interface Operation {

        CardTransaction execute(Card card);

    }

    private Card getCard(CardNumber number) {
        return cardRepository.getByNumber(number)
                .orElseThrow(CardNotFoundException::new);
    }

    private Consumer<CardCharged> createCardChargedEventListener() {
        return cardCharged -> cardEventsPublisher.publish(toApplicationEvent(cardCharged));
    }

    private CardChargedApplicationEvent toApplicationEvent(CardCharged cardCharged) {
        return new CardChargedApplicationEvent(cardCharged.number().toString());
    }

}
