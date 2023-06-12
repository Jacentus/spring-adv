package pl.training.payments.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedList;
import java.util.UUID;

public class CardFactory {

    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("PLN");

    public Card create(String owner, String cardNumber, String cvv, LocalDate expiration, BigDecimal balance) {
        var cardId = new CardId(UUID.randomUUID().toString());
        var number = new CardNumber(cardNumber);
        var verificationNumber = new CardVerificationValue(cvv);
        var cardBalance = new Money(new MonetaryAmount(balance), DEFAULT_CURRENCY);
        return new Card(cardId, owner, number, verificationNumber, expiration,  cardBalance, new ArrayList<>(), new LinkedList<>());
    }
}
