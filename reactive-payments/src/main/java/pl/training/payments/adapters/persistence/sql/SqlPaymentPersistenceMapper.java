package pl.training.payments.adapters.persistence.sql;

import org.mapstruct.Mapper;
import pl.training.payments.domain.Payment;

@Mapper(componentModel = "spring")
public interface SqlPaymentPersistenceMapper {

    PaymentEntity toEntity(Payment payment);

    Payment toDomain(PaymentEntity paymentEntity);

}
