package com.oric.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.oric.food.api.model.mixin.CidadeMixin;
import com.oric.food.api.model.mixin.RestauranteMixin;
import com.oric.food.domain.model.Cidade;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.model.Restaurante;

@Component
public class JacksonMixinModel extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModel() {
		
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class,CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, Cozinha.class); 
	}

}
