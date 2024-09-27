package br.com.willbigas.specificationspringdatajpa.controller;

import br.com.willbigas.specificationspringdatajpa.entity.Usuario;
import br.com.willbigas.specificationspringdatajpa.filter.UsuarioFilterDTO;
import br.com.willbigas.specificationspringdatajpa.repository.UsuarioRepository;
import br.com.willbigas.specificationspringdatajpa.repository.spec.DefaultSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;

	@Qualifier("usuarioSpecification")
	private final DefaultSpecification<Usuario , UsuarioFilterDTO> usuarioSpecification;

	@GetMapping("/specifications")
	public List<Usuario> bySpecification(@RequestBody UsuarioFilterDTO filtro) {
		return usuarioRepository.findAll(usuarioSpecification.toSpecification(filtro));
	}
}
