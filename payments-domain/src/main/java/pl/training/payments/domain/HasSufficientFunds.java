package pl.training.payments.domain;

import pl.training.payments.domain.common.Specification;

public class HasSufficientFunds implements Specification {

    private final Money balance;
    private final CardTransaction transaction;

    public static HasSufficientFunds create(Money balance, CardTransaction transaction) {
        return new HasSufficientFunds(balance, transaction);
    }

    private HasSufficientFunds(Money balance, CardTransaction transaction) {
        this.balance = balance;
        this.transaction = transaction;
    }

    @Override
    public boolean check() {
        return balance.amount().isGreaterOrEqual(transaction.money().amount());
    }

}
