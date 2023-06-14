package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;
import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;
import static pl.training.payments.adapters.output.persistence.jpa.CardEntity.WITH_TRANSACTIONS;

public interface JpaCardRepository extends JpaRepository<CardEntity, String>, JpaCardRepositoryExtensions, JpaSpecificationExecutor<CardEntity> {   // Repository<CardEntity, String>, CrudRepository<CardEntity, String>


    @Lock(LockModeType.WRITE)
    Optional<CardEntity> getByNumber(String numer);

    @Query("select new pl.training.payments.adapters.output.persistence.jpa.BaseCardView(c.number, c.balance, c.currencyCode) from Card c where c.balance >= :value")
    List<BaseCardView> getAllWithValue(BigDecimal value);

    @Query("select c.number as number, c.balance as balance, c.currencyCode as currencyCode from Card c where c.balance >= :value")
    Stream<CardProjection> getAllWithValueAsStream(BigDecimal value);

    // LOAD - All attributes specified in entity graph will be treated as Eager, and all attribute not specified will be treated as Lazy
    // FETCH - All attributes specified in entity graph will be treated as Eager, and all attribute not specified use their default/mapped value
    // @EntityGraph(value = WITH_TRANSACTIONS, type = FETCH)
    @EntityGraph(attributePaths = "transactions", type = FETCH)
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
