package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

public record CardVerificationCode(String value) implements ValueObject {

    private static final String CODE_PATTERN = "\\d{3,4}";

    public CardVerificationCode {
        if (!value.matches(CODE_PATTERN)) {
            throw new IllegalArgumentException();
        }
    }

}
