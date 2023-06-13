package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Setter;

import java.util.List;

public class JpaCardRepositoryImpl implements JpaCardRepositoryExtensions {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CardEntity> getWithBalanceEqualsZero() {
        return entityManager.createNamedQuery(CardEntity.GET_WITH_ZERO_BALANCE, CardEntity.class)
                .getResultList();
    }

}
