package pl.training.payments.application.input.commands;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.services.PaymentService;

@RequiredArgsConstructor
public class ChargeCardCommandHandler {

    private final PaymentService paymentService;

    public void handle(ChargeCardCommand chargeCardCommand) {
        paymentService.chargeCard(chargeCardCommand.number(), chargeCardCommand.amount());
    }

}
