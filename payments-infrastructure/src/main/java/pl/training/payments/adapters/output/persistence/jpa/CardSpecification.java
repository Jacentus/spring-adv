package pl.training.payments.adapters.output.persistence.jpa;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@RequiredArgsConstructor
public class CardSpecification implements Specification<CardEntity> {

    private final Set<SearchCriteria> searchCriteria;

    @Override
    public Predicate toPredicate(Root<CardEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder
                .and(searchCriteria.stream().map(criteria -> map(criteria, root, criteriaBuilder))
                .toArray(Predicate[]::new));
    }

    private Predicate map(SearchCriteria criteria, Root<CardEntity> root, CriteriaBuilder builder) {
        var property = criteria.getPropertyName();
        var value = criteria.getValue();
        return switch (criteria.getMatcher()) {
            case EQUAL -> builder.equal(root.get(property), value);
            case NOT_EQUAL -> builder.notEqual(root.get(property), value);
            case START_WITH -> builder.like(root.get(property), value + "%");
        };
    }

}
