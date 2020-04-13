package com.oric.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.exception.RestauranteNaoEncontradoException;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.model.Restaurante;
import com.oric.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.setAtivo(true);
		
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.setAtivo(false);
		
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
