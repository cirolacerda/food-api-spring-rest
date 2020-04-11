package com.oric.food.api.model.mixin;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oric.food.domain.model.Estado;

@Component
public abstract class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;

}
