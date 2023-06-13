package pl.training.payments.domain;

import java.time.LocalDate;
import java.util.*;

public class CardFactory {

    public Card create(String owner, String cardNumber, String cvv, LocalDate expiration, double balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var number = new CardNumber(cardNumber);
        var verificationNumber = new CardVerificationCode(cvv);
        var cardBalance = Money.of(balance);
        return new Card(cardId, owner, number, verificationNumber, expiration,  cardBalance, new ArrayList<>(), new LinkedList<>());
    }

    public Card create(String id, String owner, String cardNumber, String cvv, LocalDate expiration, double balance, String currencyCode, List<CardTransaction> transactions) {
        var cardId = new CardId(id);
        var number = new CardNumber(cardNumber);
        var verificationNumber = new CardVerificationCode(cvv);
        var cardBalance = new Money(MonetaryAmount.of(balance), Currency.getInstance(currencyCode));
        return new Card(cardId, owner, number, verificationNumber, expiration,  cardBalance, transactions, new LinkedList<>());
    }

}
