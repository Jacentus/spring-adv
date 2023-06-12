package pl.training.payments.application.input.commands;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.services.PaymentService;

@RequiredArgsConstructor
public class ChargeCardFeesCommandHandler {

    private final PaymentService paymentService;

    public void handle(ChargeCardFeesCommand chargeCardFeesCommand) {
        var cardNumber = chargeCardFeesCommand.number();
        paymentService.chargeFees(cardNumber);
    }

}
