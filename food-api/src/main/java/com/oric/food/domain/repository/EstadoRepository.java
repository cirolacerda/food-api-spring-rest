package com.oric.food.domain.repository;

import java.util.List;

import com.oric.food.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> todos();
	Estado porId(Long id);
	Estado adicionar(Estado estado);
	void remover(Estado estado);
	

}
