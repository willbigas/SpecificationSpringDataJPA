package br.com.willbigas.specificationspringdatajpa.repository.spec;

import br.com.willbigas.specificationspringdatajpa.entity.Pessoa;
import br.com.willbigas.specificationspringdatajpa.entity.Pessoa_;
import br.com.willbigas.specificationspringdatajpa.entity.Usuario_;
import br.com.willbigas.specificationspringdatajpa.filter.UsuarioFilterDTO;
import br.com.willbigas.specificationspringdatajpa.entity.Usuario;
import br.com.willbigas.specificationspringdatajpa.entity.enums.StatusEnum;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class UsuarioSpecification implements DefaultSpecification<Usuario, UsuarioFilterDTO> {

	@Override
	public Specification<Usuario> toSpecification(UsuarioFilterDTO filter) {
		return (root, query, builder) -> {
			Join<Usuario, Pessoa> pessoaJoin = root.join(Usuario_.PESSOA);

			return builder.and(
					porNome(filter.getNome(), pessoaJoin, builder),
					nascidoAposAno(filter.getDataNascimento(), pessoaJoin, builder),
					idadeMaiorQue(filter.getIdadeMaiorQue(), pessoaJoin, builder),
					statusIgualA(filter.getStatus(), root, builder),
					ordenarPor(filter.getAtributoOrdenacao(), filter.isAsc(), root, query, builder)
			);
		};
	}

	private Predicate porNome(String nome, Join<Usuario, Pessoa> pessoaJoin, CriteriaBuilder builder) {
		if (Objects.nonNull(nome)) {
			return builder.equal(pessoaJoin.get(Pessoa_.NOME), nome);
		}
		return builder.conjunction();
	}

	private Predicate nascidoAposAno(LocalDate dataNascimento, Join<Usuario, Pessoa> pessoaJoin, CriteriaBuilder builder) {
		if (Objects.nonNull(dataNascimento)) {
			return builder.greaterThan(pessoaJoin.get(Pessoa_.DATA_NASCIMENTO), dataNascimento);
		}
		return builder.conjunction();
	}

	private Predicate idadeMaiorQue(int idade, Join<Usuario, Pessoa> pessoaJoin, CriteriaBuilder builder) {
		if (Objects.nonNull(idade)) {
			return builder.greaterThan(pessoaJoin.get(Pessoa_.IDADE), idade);
		}
		return builder.conjunction();
	}

	private Predicate statusIgualA(StatusEnum status, Root<Usuario> root, CriteriaBuilder builder) {
		if (Objects.nonNull(status)) {
			return builder.equal(root.get(Usuario_.STATUS), status);
		}
		return builder.conjunction();
	}

	private Predicate ordenarPor(String atributoOrdenacao, boolean asc, Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (Objects.nonNull(atributoOrdenacao)) {
			Order order = asc ? builder.asc(root.get(atributoOrdenacao)) : builder.desc(root.get(atributoOrdenacao));
			query.orderBy(order);
		}
		return builder.conjunction();
	}

}
