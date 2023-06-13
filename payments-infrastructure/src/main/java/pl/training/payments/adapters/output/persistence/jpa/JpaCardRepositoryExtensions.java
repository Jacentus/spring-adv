package pl.training.payments.adapters.output.persistence.jpa;

import java.util.List;

public interface JpaCardRepositoryExtensions {

    List<CardEntity> getWithBalanceEqualsZero();

}
