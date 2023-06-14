package pl.training.payments.domain;

import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@Value
public class Payment {

    private static final String CONFIRMED_STATUS = "CONFIRMED";

    @With
    String id;
    BigDecimal value;
    String currencyCode;
    @With
    String status;

    public boolean isConfirmed() {
        return status.equals(CONFIRMED_STATUS);
    }

    public Payment confirmed() {
        return withStatus(CONFIRMED_STATUS);
    }

}
