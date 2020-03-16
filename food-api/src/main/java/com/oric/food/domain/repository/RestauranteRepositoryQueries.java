package com.oric.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.oric.food.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis( String nome);
	
	
	
	

}