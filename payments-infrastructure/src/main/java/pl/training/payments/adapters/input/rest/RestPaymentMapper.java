package pl.training.payments.adapters.input.rest;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pl.training.payments.application.input.commands.ChargeCardCommand;
import pl.training.payments.application.input.commands.ChargeCardFeesCommand;
import pl.training.payments.application.input.queries.GetCardTransactionsQuery;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.MonetaryAmount;
import pl.training.payments.domain.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, imports = {CardNumber.class, Money.class, MonetaryAmount.class, BigDecimal.class, Currency.class})
public interface RestPaymentMapper {

    @Mapping(target = "number", expression = "java(new CardNumber(chargeRequestDto.getCardNumber()))")
    @Mapping(target = "amount", expression = "java(new Money(new MonetaryAmount(BigDecimal.valueOf(chargeRequestDto.getAmount())), Currency.getInstance(chargeRequestDto.getCurrencyCode())))")
    ChargeCardCommand toDomain(ChargeRequestDto chargeRequestDto);

    @Mapping(target = "number", expression = "java(new CardNumber(chargeFeesDto.getCardNumber()))")
    ChargeCardFeesCommand toDomain(ChargeFeesDto chargeFeesDto);

    @Mapping(target = "timestamp", expression = "java(transaction.timestamp().toString())")
    @Mapping(target = "amount", expression = "java(transaction.money().amount().value().toString() + ' ' + transaction.money().currency().getCurrencyCode())")
    CardTransactionDto toDto(CardTransaction transaction);

    @IterableMapping(elementTargetType = CardTransactionDto.class)
    List<CardTransactionDto> toDtos(List<CardTransaction> transactions);

    default GetCardTransactionsQuery toDomain(String cardNumber) {
        return new GetCardTransactionsQuery(new CardNumber(cardNumber));
    }

}
