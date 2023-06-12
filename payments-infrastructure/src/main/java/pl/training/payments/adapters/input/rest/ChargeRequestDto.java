package pl.training.payments.adapters.input.rest;

import lombok.Data;

@Data
public class ChargeRequestDto {

    private String cardNumber;
    private double amount;
    private String currencyCode;

}
