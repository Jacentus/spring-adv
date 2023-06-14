package pl.training.payments.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class CardFactory {

    public Card create(String cardNumber, LocalDate expiration, double balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var number = new CardNumber(cardNumber);
        var cardBalance = Money.of(BigDecimal.valueOf(balance));
        return new Card(cardId, number, expiration, cardBalance, new ArrayList<>());
    }
}
