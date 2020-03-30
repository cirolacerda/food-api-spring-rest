package com.oric.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}

}
