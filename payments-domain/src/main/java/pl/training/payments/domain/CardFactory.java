package pl.training.payments.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class CardFactory {

    public Card create(String owner, String cardNumber, String cvv, LocalDate expiration, double balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var number = new CardNumber(cardNumber);
        var verificationNumber = new CardVerificationCode(cvv);
        var cardBalance = Money.of(balance);
        return new Card(cardId, owner, number, verificationNumber, expiration,  cardBalance, new ArrayList<>(), new LinkedList<>());
    }
}
