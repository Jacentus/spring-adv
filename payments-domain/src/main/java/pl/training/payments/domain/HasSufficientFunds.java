package pl.training.payments.domain;

import pl.training.payments.domain.common.Specification;

public class HasSufficientFunds implements Specification {

    private final MonetaryAmount balance;
    private final CardTransaction transaction;

    public static HasSufficientFunds create(MonetaryAmount balance, CardTransaction transaction) {
        return new HasSufficientFunds(balance, transaction);
    }

    private HasSufficientFunds(MonetaryAmount balance, CardTransaction transaction) {
        this.balance = balance;
        this.transaction = transaction;
    }

    @Override
    public boolean check() {
        return balance.isGreaterOrEqual(transaction.amount());
    }

}
