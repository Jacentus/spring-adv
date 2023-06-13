package pl.training.payments.adapters.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.commons.Page;
import pl.training.payments.commons.ResultPage;
import pl.training.payments.domain.Payment;
import pl.training.payments.domain.PaymentStatus;
import pl.training.payments.ports.PaymentRepository;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
@Repository
@RequiredArgsConstructor
public class JpaPaymentRepositoryAdapter implements PaymentRepository {

    private final JpaPaymentRepository paymentRepository;
    private final JpaPaymentPersistenceMapper mapper;

    @Override
    public Payment save(Payment payment) {
        var paymentEntity = mapper.toEntity(payment);
        return mapper.toDomain(paymentRepository.save(paymentEntity));
    }

    @Override
    public Optional<Payment> getById(String id) {
        return paymentRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public ResultPage<Payment> getByStatus(PaymentStatus paymentStatus, Page page) {
        var status = mapper.toEntity(paymentStatus);
        var result = paymentRepository.getByStatus(status, PageRequest.of(page.getNumber(), page.getSize()));
        return mapper.toDomain(result);
    }

}
