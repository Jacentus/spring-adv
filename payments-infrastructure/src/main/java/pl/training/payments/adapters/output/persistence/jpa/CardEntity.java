package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NamedEntityGraph(name = CardEntity.WITH_TRANSACTIONS, attributeNodes = @NamedAttributeNode(value = "transactions"))
@NamedQuery(name = CardEntity.GET_WITH_ZERO_BALANCE, query = "select c from Card c where c.balance = 0")
@Entity(name = "Card")
@Getter
@Setter
public class CardEntity {

    public static final String GET_WITH_ZERO_BALANCE = "CardEntity.GET_WITH_ZERO_BALANCE";
    public static final String WITH_TRANSACTIONS = "CardEntity.WITH_TRANSACTIONS";

    @Id
    private String id;
    @Column(name = "CARD_NUMBER", unique = true)
    private String number;
    private LocalDate expirationDate;
    private BigDecimal balance;
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Column(length = 10_000)
    private String transactions;

    /*@OneToMany(fetch = FetchType.EAGER)
    private List<TransactionEntity> transactions;*/

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
