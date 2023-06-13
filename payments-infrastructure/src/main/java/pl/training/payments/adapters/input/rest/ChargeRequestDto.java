package pl.training.payments.adapters.input.rest;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChargeRequestDto {

    @Pattern(regexp = "\\d{16,19}")
    private String cardNumber;
    private double amount;
    private String currencyCode;

}
