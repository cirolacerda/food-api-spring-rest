package com.oric.food.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.model.FormaPagamento;
import com.oric.food.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoImpl implements FormaPagamentoRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<FormaPagamento> todas() {

		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaPagamento", FormaPagamento.class);

		return query.getResultList();
	}

	@Override
	public FormaPagamento porId(Long id) {

		return manager.find(FormaPagamento.class, id);

	}
	
    @Transactional
	@Override
	public FormaPagamento adicionar(FormaPagamento formaPagamento) {

		return manager.merge(formaPagamento);
	}

    @Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = porId(formaPagamento.getId());
		manager.remove(formaPagamento);
		
	}

}
