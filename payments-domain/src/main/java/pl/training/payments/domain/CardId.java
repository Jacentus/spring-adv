package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

public record CardId(String value) implements ValueObject {
}
