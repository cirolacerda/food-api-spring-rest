package com.oric.food.domain.repository;

import org.springframework.stereotype.Repository;

import com.oric.food.domain.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends CustomJpaRepository<ItemPedido, Long> {

}
