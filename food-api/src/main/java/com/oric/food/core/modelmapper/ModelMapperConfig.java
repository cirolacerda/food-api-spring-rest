package com.oric.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oric.food.api.model.RestauranteModel;
import com.oric.food.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
				
				modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                    .addMapping(restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(), 
                	(restDest, val) -> restDest.getEndereco().getCidade().setEstado((String) val));
		
		return modelMapper;
	}
}
