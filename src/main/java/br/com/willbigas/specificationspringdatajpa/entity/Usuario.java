package br.com.willbigas.specificationspringdatajpa.entity;

import br.com.willbigas.specificationspringdatajpa.entity.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login;

	private String senha;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Pessoa pessoa;

	@Enumerated(EnumType.STRING)
	private StatusEnum status;

}
