package pl.training.payments.adapters.rest;

import lombok.Data;
import pl.training.payments.commons.data.validation.Money;

@Data
public class PaymentRequestDto {

    @Money
    private String value;

}
