package pl.training.payments.domain;

import pl.training.payments.domain.common.Policy;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.TEN;

public class TransactionBasedFees implements Policy<MonetaryAmount> {

    private static final MonetaryAmount PER_TRANSACTION_FEE = new MonetaryAmount(TEN);

    private final List<CardTransaction> transactions;

    public TransactionBasedFees(List<CardTransaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public MonetaryAmount execute() {
        var transactionsCount = BigDecimal.valueOf(transactions.size());
        return PER_TRANSACTION_FEE.multiplyBy(transactionsCount);
    }
}
