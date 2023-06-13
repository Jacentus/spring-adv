package pl.training.payments.adapters.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {

    Page<PaymentEntity> getByStatus(String status, Pageable pageable);

    @Query("select p from Payment p where p.status = 'COMPLETED' and p.value >= :value")
    Page<PaymentEntity> getCompletedWithValue(/*@Param("value")*/ BigDecimal value, Pageable pageable);

}
