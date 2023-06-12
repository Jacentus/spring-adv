package pl.training.payments.application.input.commands;

import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.Money;

public record ChargeCard(CardNumber number, Money amount) {
}
