package com.oric.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oric.food.api.assembler.PermissaoInputDisassembler;
import com.oric.food.api.assembler.PermissaoModelAssembler;
import com.oric.food.api.model.PermissaoModel;
import com.oric.food.api.model.input.PermissaoInput;
import com.oric.food.domain.model.Permissao;
import com.oric.food.domain.repository.PermissaoRepository;
import com.oric.food.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private PermissaoInputDisassembler permissaoInputDisassembler;

	@GetMapping
	public List<PermissaoModel> listar() {

		List<Permissao> todasPermissoes = permissaoRepository.findAll();

		return permissaoModelAssembler.toCollectionModel(todasPermissoes);
	}

	@GetMapping("/{permissaoId}")
	public PermissaoModel buscar(@PathVariable Long permissaoId) {

		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);

		return permissaoModelAssembler.toModel(permissao);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoModel adicionar(@RequestBody @Valid PermissaoInput permissaoInput) {

		Permissao permissao = permissaoInputDisassembler.toDomainObject(permissaoInput);

		permissao = cadastroPermissao.salvar(permissao);

		return permissaoModelAssembler.toModel(permissao);

	}
	
	@DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long permissaoId) {
		cadastroPermissao.excluir(permissaoId);
	}

}
