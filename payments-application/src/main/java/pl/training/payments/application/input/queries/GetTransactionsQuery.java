package pl.training.payments.application.input.queries;

import pl.training.payments.domain.CardNumber;

public record GetTransactionsQuery(CardNumber number) {
}
