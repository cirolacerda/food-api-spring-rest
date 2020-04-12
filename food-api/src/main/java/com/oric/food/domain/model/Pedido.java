package com.oric.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column( name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column (name="data_criacao", nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	private StatusPedido status;
	
	@UpdateTimestamp
	@Column(name = "data_confirmacao", columnDefinition = "datetime")
	private OffsetDateTime dataConfirmacao;
	
	@Column(name = "data_cancelamento", columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;
	
	@Column(name = "data_entrega", columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;
	
	@ManyToOne
	@JoinColumn (nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
	

}
