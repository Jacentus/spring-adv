package pl.training.payments.adapters.output.persistence.mongo;

import pl.training.payments.domain.CardTransactionType;
import pl.training.payments.domain.Money;

public record CardTransactionDocument(String timestamp, Money money, CardTransactionType type){
}
