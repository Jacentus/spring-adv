package pl.training.payments.domain;

import org.javamoney.moneta.Money;

public interface PaymentFeeCalculator {

    Money calculateFee(Money paymentValue);

}
