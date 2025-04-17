package com.pedido.servicioPedido.repository;

import com.pedido.servicioPedido.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
