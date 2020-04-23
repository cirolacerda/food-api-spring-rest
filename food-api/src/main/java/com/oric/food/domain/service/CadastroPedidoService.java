package com.oric.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.exception.EntidadeEmUsoException;
import com.oric.food.domain.exception.PedidoNaoEncontradoException;
import com.oric.food.domain.model.Pedido;
import com.oric.food.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	private static final String MSG_PEDIDO_EM_USO = "Pedido de código %d não pode ser removido, pois está em uso";

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void excluir(Long pedidoId) {
		try {
				pedidoRepository.deleteById(pedidoId);
				pedidoRepository.flush();
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_EM_USO, pedidoId));
			
		}catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(pedidoId);
		}
	}
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		
		return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId)); 
	}
	
}

