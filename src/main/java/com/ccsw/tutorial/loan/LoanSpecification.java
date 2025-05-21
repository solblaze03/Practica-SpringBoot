package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria searchCriteria){
        this.criteria = searchCriteria;
    }


    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {

            Path<String> path = getPath(root);
            if(path.getJavaType() == String.class){
                return criteriaBuilder.like(path,  criteria.getValue()+"");
            }else{
                return criteriaBuilder.equal(path, criteria.getValue());
            }
        }else if(criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null){
            Path<LocalDate> path = getPath(root);
            return criteriaBuilder.lessThanOrEqualTo(path, (LocalDate) criteria.getValue());
        }else if(criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null){
            Path<LocalDate> path = getPath(root);
            return criteriaBuilder.greaterThanOrEqualTo(path, (LocalDate) criteria.getValue());


        }
        return null;
    }

    private <T>Path <T> getPath (Root<Loan> root){
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<T> expression = root.get(split[0]);

        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }
        return expression;
    }

}
