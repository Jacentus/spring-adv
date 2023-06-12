package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

public record CardVerificationValue(String value) implements ValueObject {

    private static final String VALUE_PATTERN = "\\d{3,4}";

    public CardVerificationValue {
        if (!value.matches(VALUE_PATTERN)) {
            throw new IllegalArgumentException();
        }
    }

}
