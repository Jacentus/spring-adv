package pl.training.payments.adapters.output.stream;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    private String id;
    private BigDecimal value;
    private String currencyCode;
    private String status;

}
