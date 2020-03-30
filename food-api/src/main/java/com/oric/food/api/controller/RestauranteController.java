package com.oric.food.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping
	public List<Restaurante> listar(){
		
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar( @PathVariable("restauranteId") Long id ) {
		
		return cadastroRestaurante.buscarOuFalhar(id);
		
	}
	
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar( @PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
	
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
					
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			
			
			
			return cadastroRestaurante.salvar(restauranteAtual);
		
		
	}

}
