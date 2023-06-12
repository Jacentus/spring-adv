package pl.training.payments.domain;

import pl.training.payments.domain.common.DomainEvent;

public record CardCharged(CardNumber number, CardTransaction transaction) implements DomainEvent {
}
