package com.pedido.servicioPedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    private Long clienteId;
    private List<PedidoDetalleRequest> detalles;
}

