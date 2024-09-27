package br.com.willbigas.specificationspringdatajpa.filter;

import br.com.willbigas.specificationspringdatajpa.entity.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class UsuarioFilterDTO {

	@JsonProperty("nome")
	private String nome;

	@JsonProperty("login")
	private String login;

	private int idadeMaiorQue;

	private StatusEnum status;

	private LocalDate dataNascimento;

	private String atributoOrdenacao;

	private boolean asc;

}
