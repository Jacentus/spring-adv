package pl.training.payments.adapters.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
public class PaymentDto {

    private String id;
    private BigDecimal value;
    private String currencyCode;
    @JsonProperty(access = READ_ONLY)
    private String status;

}
