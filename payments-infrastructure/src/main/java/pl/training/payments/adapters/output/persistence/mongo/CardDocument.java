package pl.training.payments.adapters.output.persistence.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
public class CardDocument {

    @Id
    private String id;
    private String owner;
    @Indexed(unique = true)
    private String number;
    private String verificationCode;
    private LocalDate expirationDate;
    private BigDecimal balance;
    private String currencyCode;
    private List<CardTransactionDocument> transactions;

}
