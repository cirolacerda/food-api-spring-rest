package com.oric.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
		
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format("Não existe um cadastro de pedido com código %d", pedidoId));
	}
	
	

}
