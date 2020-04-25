package com.oric.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.exception.EntidadeEmUsoException;
import com.oric.food.domain.exception.NegocioException;
import com.oric.food.domain.exception.PedidoNaoEncontradoException;
import com.oric.food.domain.model.Cidade;
import com.oric.food.domain.model.FormaPagamento;
import com.oric.food.domain.model.Pedido;
import com.oric.food.domain.model.Produto;
import com.oric.food.domain.model.Restaurante;
import com.oric.food.domain.model.StatusPedido;
import com.oric.food.domain.model.Usuario;
import com.oric.food.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	private static final String MSG_PEDIDO_EM_USO = "Pedido de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroProdutoService cadastroProduto;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		validarPedido(pedido);
	    validarItens(pedido);

	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();
	    pedido.setStatus(StatusPedido.CRIADO);
	    
	    
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void excluir(Long pedidoId) {
		try {
				pedidoRepository.deleteById(pedidoId);
				pedidoRepository.flush();
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_EM_USO, pedidoId));
			
		}catch (EmptyResultDataAccessException e) {
			throw new PedidoNaoEncontradoException(pedidoId);
		}
	}
	
	private void validarPedido(Pedido pedido) {
	    Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
	    Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
	    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
	    FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

	    pedido.getEnderecoEntrega().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}

	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = cadastroProduto.buscarOuFalhar(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		
		return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId)); 
	}
	
}

