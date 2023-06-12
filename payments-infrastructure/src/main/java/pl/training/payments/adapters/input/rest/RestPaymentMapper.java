package pl.training.payments.adapters.input.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.training.payments.application.input.commands.ChargeCard;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring", imports = { CardNumber.class, MonetaryAmount.class, BigDecimal.class, Currency.class })
public interface RestPaymentMapper {

    @Mapping(target = "number", expression = "java(new CardNumber(chargeRequestDto.cardNumber))")
    @Mapping(target = "amount", expression = "java(new MonetaryAmount(BigDecimal.valueOf(chargeRequestDto.amount), Currency.getInstance(chargeRequestDto.currencyCode)))")
    ChargeCard toDomain(ChargeRequestDto chargeRequestDto);

}
