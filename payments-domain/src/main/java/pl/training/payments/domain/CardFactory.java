package pl.training.payments.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class CardFactory {

    public Card create(String number, LocalDate expiration, double balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var cardNumber = new CardNumber(number);
        var cardBalance = Money.of(BigDecimal.valueOf(balance));
        return new Card(cardId, cardNumber, expiration, cardBalance, new ArrayList<>());
    }
}
