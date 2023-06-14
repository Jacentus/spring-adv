package pl.training.payments.adapters.output.persistence.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.math.BigDecimal;
import java.util.Optional;

public interface MongoCardRepository extends MongoRepository<CardDocument, String> {

    Optional<CardDocument> getByNumber(String numer);

    @Query(value = "{balance: {$eq: ?0}}", fields = "{number: 1, balance: 1, currencyCode: 1}")
    Page<CardDocument> getAllWithValue(BigDecimal value, Pageable pageable);

}
