package pl.training.payments.domain;

import pl.training.payments.domain.common.Policy;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static java.math.BigDecimal.TEN;

public class TransactionBasedFees implements Policy<Money> {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.getDefault());
    private static final MonetaryAmount PER_TRANSACTION_FEE = new MonetaryAmount(TEN);

    private final List<CardTransaction> transactions;

    public TransactionBasedFees(List<CardTransaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public Money execute() {
        var transactionsCount = BigDecimal.valueOf(transactions.size());
        return new Money(PER_TRANSACTION_FEE.multiplyBy(transactionsCount), DEFAULT_CURRENCY);
    }
}
