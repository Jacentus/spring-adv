package pl.training.payments.domain;

import pl.training.payments.domain.common.Entity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import static java.util.Collections.unmodifiableList;
import static pl.training.payments.domain.TransactionType.FEE;

public class Card implements Entity {

    private CardId id;
    private String owner;
    private CardNumber number;
    private CardVerificationValue cvv;
    private LocalDate expirationDate;
    private Money balance;
    private List<CardTransaction> transactions;
    private Queue<CardCharged> events;

    public Card(CardId id, String owner, CardNumber number, CardVerificationValue cvv, LocalDate expirationDate, Money balance, List<CardTransaction> transactions, Queue<CardCharged> events) {
        this.id = id;
        this.owner = owner;
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.transactions = transactions;
        this.events = events;
    }

    public void addTransaction(CardTransaction transaction) {
        if (!CardNotExpired.create(transaction.getDate(), expirationDate).check()) {
            throw new CardExpiredException();
        }
        if (!transaction.isFee() && !HasSufficientFunds.create(balance, transaction).check()) {
            throw new InsufficientFundsException();
        }
        transactions.add(transaction);
        publishCardChargeEvent(transaction);
    }

    public List<CardTransaction> getTransactions() {
        return unmodifiableList(transactions);
    }

    private void publishCardChargeEvent(CardTransaction transaction) {
        var cardChargedEvent = new CardCharged(number, transaction);
        events.add(cardChargedEvent);
    }

    public void chargeFees(ZonedDateTime timestamp) {
        var fees = new TransactionBasedFees(transactions).execute();
        var transaction = new CardTransaction(timestamp, fees, FEE);
        addTransaction(transaction);
    }

    public Queue<CardCharged> getEvents() {
        return events;
    }

    public CardNumber getNumber() {
        return number;
    }

}
