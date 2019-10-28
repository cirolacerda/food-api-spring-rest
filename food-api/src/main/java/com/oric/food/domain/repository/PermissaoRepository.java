package com.oric.food.domain.repository;

import java.util.List;

import com.oric.food.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> todas();
	Permissao porId(Long id);
	Permissao adicionar(Permissao permissao);
	void remover(Permissao permissao);
	

}
