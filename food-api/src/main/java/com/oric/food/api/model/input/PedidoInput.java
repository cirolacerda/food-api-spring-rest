package com.oric.food.api.model.input;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {
	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	private RestauranteIdInput restaurante;
	private UsuarioIdInput cliente;
	private FormaPagamentoIdInput formaPagamento;
	private EnderecoInput enderecoEntrega;
	

}
