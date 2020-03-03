package com.oric.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oric.food.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
	

}
