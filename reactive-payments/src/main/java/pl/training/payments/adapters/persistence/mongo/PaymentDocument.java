package pl.training.payments.adapters.persistence.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
public class PaymentDocument {

    @Id
    private String id;
    private BigDecimal value;
    private String currencyCode;
    private String status;

}
