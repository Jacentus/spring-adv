package pl.training.payments.application.services;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.output.events.CardChargedEvent;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.domain.*;

import java.util.List;

import static pl.training.payments.domain.CardTransactionType.WITHDRAW;

// w przyszłości trzeba będzie rozdzielić tą klasę na wiele serwiswów/use casów
@RequiredArgsConstructor
public class PaymentService {

    private final CardRepository cardRepository;
    private final CardEventsPublisher cardEventsPublisher;
    private final TimeProvider timeProvider;

    public void chargeCard(CardNumber number, Money amount) {
        var card = cardRepository.getByNumber(number)
                .orElseThrow(CardNotFoundException::new);
        var transaction = new CardTransaction(timeProvider.getTimeStamp(), amount, WITHDRAW);
        card.addTransaction(transaction);
        cardRepository.save(card);
        publishCardEvents(card);
    }

    public List<CardTransaction> getTransactions(CardNumber number) {
        return cardRepository.getByNumber(number)
                .map(Card::getTransactions)
                .orElseThrow(CardNotFoundException::new);
    }

    public void chargeFees(CardNumber number) {
        var card = cardRepository.getByNumber(number)
                .orElseThrow(CardNotFoundException::new);
        card.chargeFees(timeProvider.getTimeStamp());
        cardRepository.save(card);
        publishCardEvents(card);
    }

    private void publishCardEvents(Card card) {
        card.getEvents()
                .stream()
                .map(this::toEvent)
                .forEach(cardEventsPublisher::publish);
        card.getEvents().clear();
    }

    private CardChargedEvent toEvent(CardCharged cardCharged) {
        return new CardChargedEvent(cardCharged.number().toString());
    }

}
