package com.oric.food.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oric.food.domain.exception.EntidadeEmUsoException;
import com.oric.food.domain.exception.NegocioException;
import com.oric.food.domain.exception.UsuarioNaoEncontradoException;
import com.oric.food.domain.model.Grupo;
import com.oric.food.domain.model.Usuario;
import com.oric.food.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";
	
	private static final String MSG_EMAIL_JA_EXISTE = "Já existe um usuário cadastrado com o e-mail %s";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format(MSG_EMAIL_JA_EXISTE, usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
		
	}
	
	@Transactional
	public void excluir(Long usuarioId) {
		
		try {
				usuarioRepository.deleteById(usuarioId);
				usuarioRepository.flush();
				
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		}
		
	}
	
	@Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }

	
	public Usuario buscarOuFalhar(Long usuarioId) {
		
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(()-> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario =  buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
		
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
}
