package pl.training.payments.adapters.rest;

import org.mapstruct.Mapper;
import pl.training.payments.domain.Payment;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {

    PaymentDto toDto(Payment payment);

    Payment toDomain(PaymentDto paymentDto);

}
