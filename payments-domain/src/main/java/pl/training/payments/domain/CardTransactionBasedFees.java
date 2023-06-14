package pl.training.payments.domain;

import pl.training.payments.domain.common.Policy;

import java.math.BigDecimal;
import java.util.List;

public class CardTransactionBasedFees implements Policy<Money> {

    private static final Money SINGLE_TRANSACTION_FEE = Money.of(BigDecimal.valueOf(0.1));

    private final List<CardTransaction> transactions;

    public CardTransactionBasedFees(List<CardTransaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Money execute() {
        var transactionsCount = transactions.size();
        return SINGLE_TRANSACTION_FEE.multiplyBy(transactionsCount);
    }

}
