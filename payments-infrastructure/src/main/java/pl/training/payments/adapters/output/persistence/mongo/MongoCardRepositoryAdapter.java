package pl.training.payments.adapters.output.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import pl.training.payments.common.Adapter;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardRepository;

import java.util.Optional;

@Primary
@Adapter
@RequiredArgsConstructor
public class MongoCardRepositoryAdapter implements CardRepository {

    private final MongoCardRepository cardRepository;
    private final MongoCardRepositoryMapper mapper;

    @Override
    public Optional<Card> getByNumber(CardNumber number) {
        var cardNumber = mapper.toDocument(number);
        return cardRepository.getByNumber(cardNumber)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Card card) {
        var cardDocument = mapper.toDocument(card);
        cardRepository.save(cardDocument);
    }

}
