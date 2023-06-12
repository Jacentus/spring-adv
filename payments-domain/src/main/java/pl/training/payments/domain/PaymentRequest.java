package pl.training.payments.domain;

import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class PaymentRequest {

    Money value;

}
