package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.math.BigDecimal;
import java.util.Currency;

import static java.math.BigDecimal.ZERO;

public record Money(BigDecimal amount, Currency currency) implements ValueObject {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("PLN");

    public static Money of(BigDecimal value) {
        return new Money(value, DEFAULT_CURRENCY);
    }

    public static Money of(BigDecimal value, Currency currency) {
        return new Money(value, currency);
    }

    public Money {
        if (amount.compareTo(ZERO) < 1) {
            throw new IllegalArgumentException();
        }
    }

    public Money add(Money money) {
        checkCurrencyCompatibility(money);
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(Money money) {
        checkCurrencyCompatibility(money);
        return new Money(amount.subtract(money.amount), currency);
    }

    public Money multiplyBy(int value) {
        return new Money(amount.multiply(BigDecimal.valueOf(value)), currency);
    }

    public boolean isGreaterOrEqual(Money money) {
        checkCurrencyCompatibility(money);
        return amount.compareTo(money.amount) > -1;
    }

    private void checkCurrencyCompatibility(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException();
        }
    }

}
