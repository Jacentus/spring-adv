package pl.training.payments.adapters.input.rest;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.payments.application.input.commands.ChargeCardCommand;
import pl.training.payments.application.input.queries.GetTransactionsQuery;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.MonetaryAmount;
import pl.training.payments.domain.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Mapper(componentModel = "spring", imports = { CardNumber.class, Money.class, MonetaryAmount.class, BigDecimal.class, Currency.class })
public interface RestPaymentMapper {

    @Mapping(target = "number", expression = "java(new CardNumber(chargeRequestDto.getCardNumber()))")
    @Mapping(target = "amount", expression = "java(new Money(new MonetaryAmount(BigDecimal.valueOf(chargeRequestDto.getAmount())), Currency.getInstance(chargeRequestDto.getCurrencyCode())))")
    ChargeCardCommand toDomain(ChargeRequestDto chargeRequestDto);

    @Mapping(target = "timestamp", expression = "java(transaction.timestamp().toString())")
    @Mapping(target = "amount", expression = "java(transaction.money().amount().value().toString() + ' ' + transaction.money().currency().getCurrencyCode())")
    CardTransactionDto toDto(CardTransaction transaction);

    @IterableMapping(elementTargetType = CardTransactionDto.class)
    List<CardTransactionDto> toDtos(List<CardTransaction> transactions);

    default GetTransactionsQuery toDomain(String cardNumber) {
        return new GetTransactionsQuery(new CardNumber(cardNumber));
    }

}
