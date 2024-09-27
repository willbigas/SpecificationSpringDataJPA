package br.com.willbigas.specificationspringdatajpa;

import br.com.willbigas.specificationspringdatajpa.entity.Pessoa;
import br.com.willbigas.specificationspringdatajpa.entity.Usuario;
import br.com.willbigas.specificationspringdatajpa.entity.enums.StatusEnum;
import br.com.willbigas.specificationspringdatajpa.filter.UsuarioFilterDTO;
import br.com.willbigas.specificationspringdatajpa.repository.UsuarioRepository;
import br.com.willbigas.specificationspringdatajpa.repository.spec.DefaultSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
public class SpecificationSpringDataJpaApplication {

	@Qualifier("usuarioSpecification")
	private final DefaultSpecification<Usuario , UsuarioFilterDTO> usuarioSpecification;

	public static void main(String[] args) {
		SpringApplication.run(SpecificationSpringDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UsuarioRepository repository) {
		return args -> {
			popularDB(repository);
			UsuarioFilterDTO
					filter = UsuarioFilterDTO.builder()
					.dataNascimento(LocalDate.of(1997, 11, 12))
					.build();
			for (Usuario usuario : repository.findAll(usuarioSpecification.toSpecification(filter))) {
				System.out.println(usuario);
			}
		};
	}


	private void popularDB(UsuarioRepository repository) {
		List<Usuario> usuarios = repository.findAll();

		if (Objects.isNull(usuarios) || usuarios.isEmpty()) {
			Usuario matheus = Usuario.builder()
					.pessoa(Pessoa.builder()
							.nome("Matheus")
							.idade(27)
							.dataNascimento(LocalDate.of(1997, 11, 12))
							.build())
					.login("matheus")
					.senha("senhaMatheus")
					.status(StatusEnum.ATIVO)
					.build();

			Usuario marcos = Usuario.builder()
					.pessoa(Pessoa.builder()
							.nome("Marcos")
							.idade(33)
							.dataNascimento(LocalDate.of(1991, 7, 23))
							.build())
					.login("marcos")
					.senha("senhaMarcos")
					.status(StatusEnum.ATIVO)
					.build();

			Usuario lucas = Usuario.builder()
					.pessoa(Pessoa.builder()
							.nome("Lucas")
							.idade(17)
							.dataNascimento(LocalDate.of(2007, 3, 2))
							.build())
					.login("lucas")
					.senha("senhaLucas")
					.status(StatusEnum.DESATIVADO)
					.build();

			Usuario joao = Usuario.builder()
					.pessoa(Pessoa.builder()
							.nome("João")
							.idade(54)
							.dataNascimento(LocalDate.of(1970, 5, 27))
							.build())
					.login("joao")
					.senha("senhaJoao")
					.status(StatusEnum.EXCLUIDO)
					.build();

			repository.saveAll(Arrays.asList(matheus, marcos, lucas, joao));
		}
	}

}
