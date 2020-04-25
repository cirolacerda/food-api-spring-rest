package com.oric.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oric.food.api.model.PedidoModel;
import com.oric.food.api.model.RestauranteModel;
import com.oric.food.api.model.input.ItemPedidoInput;
import com.oric.food.domain.model.ItemPedido;
import com.oric.food.domain.model.Pedido;
import com.oric.food.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
				
				modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
                    .addMapping(restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(), 
                	(restDest, val) -> restDest.getEndereco().getCidade().setEstado((String) val));
				
				modelMapper.createTypeMap(Pedido.class, PedidoModel.class)
                .addMapping(restSrc -> restSrc.getEnderecoEntrega().getCidade().getEstado().getNome(), 
            	(restDest, val) -> restDest.getEnderecoEntrega().getCidade().setEstado((String) val));
				
				modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
				
		return modelMapper;
	}
}
