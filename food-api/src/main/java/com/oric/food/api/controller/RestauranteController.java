package com.oric.food.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Restaurante> buscar( @PathVariable("restauranteId") Long id ) {
		
		Optional<Restaurante> restaurante =  restauranteRepository.findById(id);
		
		if(restaurante.isPresent()) {
		
		return ResponseEntity.ok(restaurante.get());
		
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> atualizar( @PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
	
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if(restauranteAtual.isPresent()) {
			
			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			
			Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
			
			return ResponseEntity.ok(restauranteSalvo);
		}
		
		return ResponseEntity.notFound().build();
		
	}

}
