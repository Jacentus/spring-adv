package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record MonetaryAmount(BigDecimal value) implements ValueObject {

    public MonetaryAmount {
        if (value.compareTo(ZERO) < 1) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isGreaterOrEqual(MonetaryAmount amount) {
        return value.compareTo(amount.value) > -1;
    }

    public MonetaryAmount multiplyBy(BigDecimal value) {
        var newValue = this.value.multiply(value);
        return new MonetaryAmount(newValue);
    }

}
