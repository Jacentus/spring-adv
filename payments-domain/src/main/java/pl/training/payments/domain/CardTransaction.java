package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static pl.training.payments.domain.TransactionType.FEE;

public record CardTransaction(ZonedDateTime timestamp, Money money, TransactionType type) implements ValueObject {

    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }

    public boolean isFee() {
        return type == FEE;
    }

}
