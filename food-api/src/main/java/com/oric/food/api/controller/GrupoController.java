package com.oric.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oric.food.api.assembler.GrupoInputDisassembler;
import com.oric.food.api.assembler.GrupoModelAssembler;
import com.oric.food.api.model.GrupoModel;
import com.oric.food.api.model.input.GrupoInput;
import com.oric.food.domain.model.Grupo;
import com.oric.food.domain.repository.GrupoRepository;
import com.oric.food.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public List<GrupoModel> listar() {

		List<Grupo> todosGrupos = grupoRepository.findAll();

		return grupoModelAssembler.toCollectionModel(todosGrupos);

	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar( @PathVariable Long grupoId) {
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
		
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar ( @PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToDomainObjetc(grupoInput, grupoAtual);
		
		grupoAtual = cadastroGrupoService.salvar(grupoAtual);
		
		return grupoModelAssembler.toModel(grupoAtual);
	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar (@RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupo =  grupoInputDisassembler.toDomainObject(grupoInput);
		
	    grupo = cadastroGrupoService.salvar(grupo);
	    
	    return grupoModelAssembler.toModel(grupo);
	}
	
	@DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupoService.excluir(grupoId);	
    }   

}
