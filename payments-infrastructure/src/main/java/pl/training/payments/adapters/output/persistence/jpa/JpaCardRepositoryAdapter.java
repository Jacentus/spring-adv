package pl.training.payments.adapters.output.persistence.jpa;

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
public class JpaCardRepositoryAdapter implements CardRepository {

    private final JpaCardRepository cardRepository;
    private final JpaCardRepositoryMapper mapper;

    @Override
    public Optional<Card> getByNumber(CardNumber number) {
        var cardNumber = mapper.toEntity(number);
        return cardRepository.getByNumber(cardNumber)
                .map(mapper::toDomain);
    }

    @Override
    public void save(Card card) {
        var cardEntity = mapper.toEntity(card);
        cardRepository.save(cardEntity);
    }

}
