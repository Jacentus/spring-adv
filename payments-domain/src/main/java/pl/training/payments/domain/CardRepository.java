package pl.training.payments.domain;

import pl.training.payments.domain.common.Repository;

import java.util.Optional;

public interface CardRepository extends Repository {

    Optional<Card> getByNumber(CardNumber number);

    void save(Card card);

}
