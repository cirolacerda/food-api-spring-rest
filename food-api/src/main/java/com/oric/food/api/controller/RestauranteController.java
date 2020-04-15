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

import com.oric.food.api.assembler.RestauranteInputDisassembler;
import com.oric.food.api.assembler.RestauranteModelAssembler;
import com.oric.food.api.model.RestauranteModel;
import com.oric.food.api.model.input.RestauranteInput;
import com.oric.food.domain.exception.CidadeNaoEncontradaException;
import com.oric.food.domain.exception.CozinhaNaoEncontradaException;
import com.oric.food.domain.exception.NegocioException;
import com.oric.food.domain.model.Restaurante;
import com.oric.food.domain.repository.RestauranteRepository;
import com.oric.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteModel> listar() {

		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			
			Restaurante restaurante =  restauranteInputDisassembler.toDomainObject(restauranteInput); 
			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));

		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable("restauranteId") Long id) {

		return restauranteModelAssembler.toModel(cadastroRestaurante.buscarOuFalhar(id));

	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, 
			@RequestBody @Valid  RestauranteInput restauranteInput) {
					
		try {
			
			//Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			//BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
			//		"produtos");
	
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		
		cadastroRestaurante.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		
		cadastroRestaurante.inativar(restauranteId);
	}
		

}
