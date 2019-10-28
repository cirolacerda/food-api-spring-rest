package com.oric.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {
	

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> todas(){
		
	 TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class );
	
		return  query.getResultList();
	}
	
	@Override
	@Transactional
	public Cozinha adicionar (Cozinha cozinha) {
		
		return manager.merge(cozinha);
	}
	
	@Override
	public Cozinha porId(Long id) {
		
		return manager.find(Cozinha.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = porId(cozinha.getId());
		manager.remove(cozinha);
		
	}
	
		

}
