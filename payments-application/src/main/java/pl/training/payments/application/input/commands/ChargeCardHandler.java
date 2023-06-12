package pl.training.payments.application.input.commands;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.services.PaymentService;

@RequiredArgsConstructor
public class ChargeCardHandler {

    private final PaymentService paymentService;

    public void handle(ChargeCard chargeCard) {
        paymentService.chargeCard(chargeCard.number(), chargeCard.amount());
    }

}
