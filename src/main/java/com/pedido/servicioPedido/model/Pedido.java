package com.pedido.servicioPedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime fecha;

    @Column(name = "status", length = 1)
    private String estado;

    @Column(name = "cliente_id")
    private Long clienteId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<PedidoDetalle> detalles;

    @Column(name = "total")
    private BigDecimal total;
}
