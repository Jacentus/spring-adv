package pl.training.payments.adapters.persistence;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import pl.training.payments.commons.ResultPage;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentStatus;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.math.BigDecimal.class, org.javamoney.moneta.Money.class})
public interface JpaPaymentPersistenceMapper {

    @Mapping(target = "value", expression = "java(BigDecimal.valueOf(payment.getValue().getNumber().doubleValueExact()))")
    @Mapping(target = "currency", expression = "java(payment.getValue().getCurrency().getCurrencyCode())")
    PaymentEntity toEntity(Payment payment);

    @Mapping(target = "value", expression = "java(Money.of(paymentEntity.getValue(), paymentEntity.getCurrency()))")
    Payment toDomain(PaymentEntity paymentEntity);

    String toEntity(PaymentStatus paymentStatus);

    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toDomain(List<PaymentEntity> paymentEntities);

    @Mapping(source = "content", target = "data")
    @Mapping(source = "number", target = "pageNumber")
    ResultPage<Payment> toDomain(Page<PaymentEntity> paymentEntityPage);

}
