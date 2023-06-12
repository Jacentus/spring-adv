package pl.training.payments.application.input.commands;

import pl.training.payments.domain.CardNumber;

public record ChargeCardFeesCommand(CardNumber number) {
}
