package pl.training.payments.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

public class CardFactory {

    public Card create(String cardNumber, LocalDate expiration, double balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var number = new CardNumber(cardNumber);
        var cardBalance = Money.of(BigDecimal.valueOf(balance));
        return new Card(cardId, number, expiration, cardBalance, new ArrayList<>());
    }

    public Card create(String id, String number, LocalDate expiration, double balance, String currencyCode, List<CardTransaction> transactions) {
        var cardId = new CardId(id);
        var cardNumber = new CardNumber(number);
        var cardBalance = new Money(BigDecimal.valueOf(balance), Currency.getInstance(currencyCode));
        return new Card(cardId, cardNumber, expiration,  cardBalance, transactions);
    }

}
