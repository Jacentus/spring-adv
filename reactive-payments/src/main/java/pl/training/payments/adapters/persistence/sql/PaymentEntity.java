package pl.training.payments.adapters.persistence.sql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table(name = "payments")
@Data
public class PaymentEntity {

    @Id
    private Long id;
    @Column("amount")
    private BigDecimal value;
    @Column("currency_code")
    private String currencyCode;
    private String status;

}
