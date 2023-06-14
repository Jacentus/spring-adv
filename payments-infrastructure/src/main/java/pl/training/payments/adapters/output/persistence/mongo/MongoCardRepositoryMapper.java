package pl.training.payments.adapters.output.persistence.mongo;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardFactory;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MongoCardRepositoryMapper {

    private final CardFactory cardFactory = new CardFactory();

    public String toDocument(CardNumber number) {
        return number.value();
    }

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "number.value", target = "number")
    @Mapping(source = "cvc.value", target = "verificationCode")
    @Mapping(source = "balance.amount.value", target = "balance")
    @Mapping(source = "balance.currency.currencyCode", target = "currencyCode")
    abstract CardDocument toDocument(Card card);

    @IterableMapping(elementTargetType = CardTransactionDocument.class)
    abstract List<CardTransactionDocument> toDocument(List<CardTransaction> transactions);

    public Card toDomain(CardDocument cardEntity) {
        return cardFactory.create(cardEntity.getId(), cardEntity.getOwner(), cardEntity.getNumber(), cardEntity.getVerificationCode(),
                cardEntity.getExpirationDate(), cardEntity.getBalance().doubleValue(), cardEntity.getCurrencyCode(),
                cardEntity.getTransactions().stream().map(this::toDomain).collect(Collectors.toList()));
    }

    abstract CardTransaction toDomain(CardTransactionDocument cardTransactionDocument);

}
