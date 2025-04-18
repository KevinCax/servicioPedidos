package com.pedido.servicioPedido.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleRequest {
    @JsonAlias({"productId", "productoId"})
    private Long productoId;
    private Integer cantidad;
}
