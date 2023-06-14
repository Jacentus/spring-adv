package pl.training.payments.adapters.output.persistence.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardFactory;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Mapper(componentModel = "spring")
public abstract class JpaCardRepositoryMapper {

    private static final TypeReference<List<CardTransaction>> transactionsType = new TypeReference<>() {};

    private final ObjectMapper jsonMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module())
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final CardFactory cardFactory = new CardFactory();

    public String toEntity(CardNumber number) {
        return number.value();
    }

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "number.value", target = "number")
    @Mapping(source = "balance.amount", target = "balance")
    @Mapping(source = "balance.currency.currencyCode", target = "currencyCode")
    abstract CardEntity toEntity(Card card);

    @SneakyThrows
    public String toEntity(List<CardTransaction> transactions){
        return jsonMapper.writeValueAsString(transactions);
    }

    @SneakyThrows
    public Card toDomain(CardEntity cardEntity) {
        return cardFactory.create(cardEntity.getId(), cardEntity.getNumber(), cardEntity.getExpirationDate(),
                cardEntity.getBalance().doubleValue(), cardEntity.getCurrencyCode(),
                jsonMapper.readValue(cardEntity.getTransactions(), transactionsType));
    }

}
