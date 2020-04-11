package com.oric.food.api.model.mixin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oric.food.domain.model.Restaurante;

@Component
public abstract class CozinhaMixin {
	
	@JsonIgnore
	private List<Restaurante> restaurantes;

}
