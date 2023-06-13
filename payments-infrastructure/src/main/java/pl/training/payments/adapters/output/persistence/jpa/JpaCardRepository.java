package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface JpaCardRepository extends JpaRepository<CardEntity, String>, JpaCardRepositoryExtensions {   // Repository<CardEntity, String>, CrudRepository<CardEntity, String>

    Optional<CardEntity> getByNumber(String numer);

    @Query("select new pl.training.payments.adapters.output.persistence.jpa.BaseCardView(c.number, c.balance, c.currencyCode) from Card c where c.balance >= :value")
    List<BaseCardView> getAllWithValue(BigDecimal value);

    @Query("select c.number as number, c.balance as balance, c.currencyCode as currencyCode from Card c where c.balance >= :value")
    Stream<CardProjection> getAllWithValueAsStream(BigDecimal value);

    @Query("select c from Card c where c.balance >= :value")
    Page<CardEntity> getWithValue(/*@Param("value")*/ BigDecimal value, Pageable pageable);

    @Async
    @Query("select c from Card c")
    Future<List<CardEntity>> getAllAsync();

    @Transactional
    @Modifying
    @Query("update Card c set c.balance = c.balance + :amount")
    void updateBalance(BigDecimal amount);

}
