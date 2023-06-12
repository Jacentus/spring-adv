package pl.training.payments.domain;

import pl.training.payments.domain.common.ValueObject;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static pl.training.payments.domain.CardTransactionType.WITHDRAW;

public record CardTransaction(ZonedDateTime timestamp, Money money, CardTransactionType type) implements ValueObject {

    public LocalDate getDate() {
        return timestamp.toLocalDate();
    }

    public boolean isWithdraw() {
        return type == WITHDRAW;
    }

}
