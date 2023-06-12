package pl.training.payments.application.services;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.output.events.CardChargedEvent;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.domain.*;

import static pl.training.payments.domain.TransactionType.WITHDRAW;

@RequiredArgsConstructor
public class PaymentService {

    private final CardRepository cardRepository;
    private final TimeProvider timeProvider;
    private final CardEventsPublisher cardEventsPublisher;

    public void chargeCard(CardNumber number, Money amount) {
        var card = cardRepository.getByNumber(number)
                .orElseThrow(CardNotFoundException::new);
        var transaction = new CardTransaction(timeProvider.getTimeStamp(), amount, WITHDRAW);
        card.addTransaction(transaction);
        cardRepository.save(card);
        publishEvents(card);
    }

    public void chargeFees(CardNumber number) {
        var card = cardRepository.getByNumber(number)
                .orElseThrow(CardNotFoundException::new);
        card.chargeFees(timeProvider.getTimeStamp());
        cardRepository.save(card);
        publishEvents(card);
    }

    private void publishEvents(Card card) {
        card.getEvents()
                .stream()
                .map(this::toEvent)
                .forEach(cardEventsPublisher::publish);
    }

    private CardChargedEvent toEvent(CardCharged cardCharged) {
        return new CardChargedEvent(cardCharged.number().toString());
    }

}
