package com.pedido.servicioPedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalle {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productoId;

    @Column(name = "quantity")
    private Integer cantidad;

    @Column(name = "unit_price")
    private BigDecimal precioUnitario;
}
