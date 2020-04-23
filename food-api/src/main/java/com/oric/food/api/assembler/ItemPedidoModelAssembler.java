package com.oric.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oric.food.api.model.ItemPedidoModel;
import com.oric.food.domain.model.ItemPedido;

@Component
public class ItemPedidoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ItemPedidoModel toModel(ItemPedido itemPedido) {
		
		return modelMapper.map(itemPedido, ItemPedidoModel.class);
		
	}
	
	public List<ItemPedidoModel> toCollectionModel(Collection<ItemPedido> itensPedidos){
		
		return itensPedidos.stream()
				.map(itemPedido -> toModel(itemPedido))
				.collect(Collectors.toList());
	}
}
