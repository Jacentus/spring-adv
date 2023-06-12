package pl.training.payments.domain;

import pl.training.payments.domain.common.Specification;

import java.time.LocalDate;

public class CardNotExpired implements Specification {

    private final LocalDate localDate;
    private final LocalDate expirationDate;

    public static CardNotExpired create(LocalDate localDate, LocalDate expirationDate) {
        return new CardNotExpired(localDate, expirationDate);
    }

    public CardNotExpired(LocalDate localDate, LocalDate expirationDate) {
        this.localDate = localDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean check() {
        return localDate.isBefore(expirationDate);
    }

}
