package com.oric.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oric.food.api.assembler.PedidoModelAssembler;
import com.oric.food.api.model.PedidoModel;
import com.oric.food.domain.model.Pedido;
import com.oric.food.domain.repository.PedidoRepository;
import com.oric.food.domain.service.CadastroPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@GetMapping
	public List<PedidoModel> listar(){
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		
		return pedidoModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}
}
