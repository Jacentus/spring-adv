package pl.training.payments.application.input.queries;

import lombok.RequiredArgsConstructor;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardTransaction;

import java.util.List;

@RequiredArgsConstructor
public class GetTransactionsQueryHandler {

    private final PaymentService paymentService;

    public List<CardTransaction> handle(GetTransactionsQuery getTransactionsQuery) {
        return paymentService.getTransactions(getTransactionsQuery.number());
    }

}
