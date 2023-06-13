package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Card")
@Getter
@Setter
public class CardEntity {

    @Id
    private String id;
    private String owner;
    @Column(name = "CARD_NUMBER", unique = true)
    private String number;
    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;
    private LocalDate expirationDate;
    private BigDecimal balance;
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    private String transactions;
    @Version
    private long version;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
            return false;
        }
         var that = (CardEntity) other;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
