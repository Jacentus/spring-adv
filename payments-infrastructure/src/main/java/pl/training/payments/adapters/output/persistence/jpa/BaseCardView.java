package pl.training.payments.adapters.output.persistence.jpa;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BaseCardView {

    String number;
    BigDecimal balance;
    String currencyCode;

}
