package pl.training.payments.adapters.output.persistence;

import pl.training.payments.common.Adapter;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Adapter
public class InMemoryCardRepository implements CardRepository {

    private final Map<CardNumber, Card> cards = new HashMap<>();

    @Override
    public Optional<Card> getByNumber(CardNumber number) {
        return Optional.ofNullable(cards.get(number));
    }

    @Override
    public void save(Card card) {
        cards.put(card.getNumber(), card);
    }

}
