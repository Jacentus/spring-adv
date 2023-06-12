package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.util.Currency;

public record Money(MonetaryAmount amount, Currency currency) implements ValueObject {
}
