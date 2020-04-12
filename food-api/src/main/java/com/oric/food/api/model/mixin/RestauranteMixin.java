package com.oric.food.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.model.Endereco;
import com.oric.food.domain.model.FormaPagamento;
import com.oric.food.domain.model.Produto;

public abstract class RestauranteMixin {
	

	//@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	private OffsetDateTime dataAtualizacao;
	
		
	@JsonIgnore
	private List<FormaPagamento> formasPagamento;
	
	@JsonIgnore
	private List<Produto> produtos;
	

}
