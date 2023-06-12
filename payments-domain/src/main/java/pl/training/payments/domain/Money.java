package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.util.Currency;

public record Money(MonetaryAmount amount, Currency currency) implements ValueObject {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("PLN");

    public static Money of(double value) {
        return new Money(MonetaryAmount.of(value), DEFAULT_CURRENCY);
    }

    public Money multiplyBy(int value) {
        return new Money(amount.multiplyBy(value), currency);
    }

    public boolean isGreaterOrEqual(Money money) {
        if (!hasEqualCurrency(money)) {
            throw new IllegalArgumentException();
        }
        return amount.isGreaterOrEqual(money.amount);
    }

    public boolean hasEqualCurrency(Money money) {
        return currency.equals(money.currency);
    }

}
