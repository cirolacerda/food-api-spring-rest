package com.oric.food.domain.repository;

import org.springframework.stereotype.Repository;

import com.oric.food.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
