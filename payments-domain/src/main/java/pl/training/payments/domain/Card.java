package pl.training.payments.domain;

import pl.training.payments.domain.common.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.unmodifiableList;

public class Card implements Entity {

    private CardId id;
    private CardNumber number;
    private LocalDate expirationDate;
    private Money balance;
    private List<CardTransaction> transactions;
    private List<Consumer<CardCharged>> eventListeners = new ArrayList<>();

    public Card(CardId id, CardNumber number, LocalDate expirationDate, Money balance, List<CardTransaction> transactions) {
        this.id = id;
        this.number = number;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.transactions = transactions;
    }

    public void addTransaction(CardTransaction transaction) {
        if (!CardNotExpired.create(transaction.getDate(), expirationDate).check()) {
            throw new CardExpiredException();
        }
        if (transaction.isWithdraw() && !HasSufficientFunds.create(balance, transaction).check()) {
            throw new InsufficientFundsException();
        }
        balance = switch (transaction.type()) {
            case FEE, WITHDRAW -> balance.subtract(transaction.money());
        };
        transactions.add(transaction);
        publishCardChargeEvent(transaction);
    }

    public List<CardTransaction> getTransactions() {
        return unmodifiableList(transactions);
    }

    private void publishCardChargeEvent(CardTransaction transaction) {
        var cardChargedEvent = new CardCharged(number, transaction);
        eventListeners.forEach(consumer -> consumer.accept(cardChargedEvent));
    }

    public void addEventsListener(Consumer<CardCharged> consumer) {
        eventListeners.add(consumer);
    }

    public CardId getId() {
        return id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Money getBalance() {
        return balance;
    }

    public CardNumber getNumber() {
        return number;
    }

}
