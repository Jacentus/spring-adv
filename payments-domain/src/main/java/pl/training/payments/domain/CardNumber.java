package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

public record CardNumber(String value) implements ValueObject {

    private static final String NUMBER_PATTERN = "\\d{16,19}";

    public CardNumber {
        if (!value.matches(NUMBER_PATTERN)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
