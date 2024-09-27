package br.com.willbigas.specificationspringdatajpa.repository.spec;

import org.springframework.data.jpa.domain.Specification;

public interface DefaultSpecification<T, F> {

	Specification<T> toSpecification(F filterDTO);
}
