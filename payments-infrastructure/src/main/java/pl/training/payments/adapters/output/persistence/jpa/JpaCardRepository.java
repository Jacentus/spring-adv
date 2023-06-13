package pl.training.payments.adapters.output.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCardRepository extends JpaRepository<CardEntity, String> {   // Repository<CardEntity, String>, CrudRepository<CardEntity, String>

    Optional<CardEntity> getByNumber(String numer);

}
