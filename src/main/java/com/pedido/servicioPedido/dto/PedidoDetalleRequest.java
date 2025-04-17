package com.pedido.servicioPedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleRequest {
    private Long productoId;
    private Integer cantidad;
}
