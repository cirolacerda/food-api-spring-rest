package com.oric.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oric.food.api.assembler.PedidoInputDisassembler;
import com.oric.food.api.assembler.PedidoModelAssembler;
import com.oric.food.api.assembler.PedidoResumoModelAssembler;
import com.oric.food.api.model.PedidoModel;
import com.oric.food.api.model.PedidoResumoModel;
import com.oric.food.api.model.input.PedidoInput;
import com.oric.food.domain.exception.EntidadeNaoEncontradaException;
import com.oric.food.domain.exception.NegocioException;
import com.oric.food.domain.model.Pedido;
import com.oric.food.domain.model.Usuario;
import com.oric.food.domain.repository.PedidoRepository;
import com.oric.food.domain.repository.filter.PedidoFilter;
import com.oric.food.domain.service.CadastroPedidoService;
import com.oric.food.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public List<PedidoResumoModel> pesquisar(PedidoFilter filtro){
		List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
		
		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = cadastroPedidoService.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = cadastroPedidoService.salvar(novoPedido);

	        return pedidoModelAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
}
