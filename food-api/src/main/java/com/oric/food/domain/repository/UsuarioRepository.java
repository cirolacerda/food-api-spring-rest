package com.oric.food.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oric.food.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
