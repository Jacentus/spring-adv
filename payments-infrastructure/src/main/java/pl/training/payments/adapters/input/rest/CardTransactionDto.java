package pl.training.payments.adapters.input.rest;

import lombok.Data;

@Data
public class CardTransactionDto {

    private String timestamp;
    private String amount;
    private String type;

}
