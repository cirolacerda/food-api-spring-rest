package com.oric.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.model.Permissao;
import com.oric.food.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> todas() {
		
		TypedQuery<Permissao> query = manager.createQuery("from Permissao", Permissao.class);
		
		
		return query.getResultList();
	}

	@Override
	public Permissao porId(Long id) {
		
		return manager.find(Permissao.class, id);
		
		
	}
	
	@Transactional
	@Override
	public Permissao adicionar(Permissao permissao) {
		
		return manager.merge(permissao);
		
		
	}

	@Transactional
	@Override
	public void remover(Permissao permissao) {
		
		permissao = porId(permissao.getId());
		manager.remove(permissao);

	}

}
