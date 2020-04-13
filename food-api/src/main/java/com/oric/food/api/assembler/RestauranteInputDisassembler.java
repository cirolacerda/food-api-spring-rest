package com.oric.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oric.food.api.model.input.RestauranteInput;
import com.oric.food.domain.model.Cozinha;
import com.oric.food.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject (RestauranteInput restauranteInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInput, restaurante);
	}

}
