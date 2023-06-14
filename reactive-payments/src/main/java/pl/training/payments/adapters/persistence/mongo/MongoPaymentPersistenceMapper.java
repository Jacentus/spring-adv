package pl.training.payments.adapters.persistence.mongo;

import org.mapstruct.Mapper;
import pl.training.payments.domain.Payment;

@Mapper(componentModel = "spring")
public interface MongoPaymentPersistenceMapper {

    PaymentDocument toDocument(Payment payment);

    Payment toDomain(PaymentDocument paymentDocument);

}
