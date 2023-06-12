package pl.training.payments.application.input.queries;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardTransaction;

import java.util.List;

@RequiredArgsConstructor
public class GetCardTransactionsQueryHandler {

    private final PaymentService paymentService;

    public List<CardTransaction> handle(GetCardTransactionsQuery getCardTransactionsQuery) {
        var cardNumber = getCardTransactionsQuery.number();
        return paymentService.getTransactions(cardNumber);
    }

}
