package com.oric.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.exception.EntidadeEmUsoException;
import com.oric.food.domain.exception.ItemPedidoNaoEncontradoException;
import com.oric.food.domain.model.ItemPedido;
import com.oric.food.domain.repository.ItemPedidoRepository;

@Service
public class CadastroItemPedidoService {
	
	private static final String MSG_ITEM_PEDIDO_EM_USO = "Item Pedido de código %d não pode ser removido, pois está em uso";

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	@Transactional
	public ItemPedido salvar(ItemPedido itemPedido) {
		
		return itemPedidoRepository.save(itemPedido);
	}
	
	@Transactional
	public void excluir(Long itemPedidoId) {
		try {
				itemPedidoRepository.deleteById(itemPedidoId);
				itemPedidoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new ItemPedidoNaoEncontradoException(itemPedidoId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ITEM_PEDIDO_EM_USO, itemPedidoId));
		}
	}
	

	public ItemPedido buscarOuFalhar(Long itemPedidoId) {

		return itemPedidoRepository.findById(itemPedidoId)
				.orElseThrow(() -> new ItemPedidoNaoEncontradoException(itemPedidoId));
	}

}
