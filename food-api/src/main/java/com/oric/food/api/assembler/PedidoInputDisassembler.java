package com.oric.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oric.food.api.model.input.PedidoInput;
import com.oric.food.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInput pedidoInput) {
		
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
		 
		  modelMapper.map(pedidoInput, pedido);
	}

}
