package pl.training.payments.application.input.commands;

import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.MonetaryAmount;

public record ChargeCard(CardNumber number, MonetaryAmount amount) {
}
