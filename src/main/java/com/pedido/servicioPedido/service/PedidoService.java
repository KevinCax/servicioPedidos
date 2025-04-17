package com.pedido.servicioPedido.service;

import com.pedido.servicioPedido.dto.PedidoRequest;
import com.pedido.servicioPedido.dto.StockUpdateRequest;
import com.pedido.servicioPedido.model.Pedido;
import com.pedido.servicioPedido.model.PedidoDetalle;
import com.pedido.servicioPedido.model.client.Product;
import com.pedido.servicioPedido.model.client.ProductClient;
import com.pedido.servicioPedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductClient productClient;

    public Pedido crearPedido(PedidoRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<PedidoDetalle> detalles = new ArrayList<>();

        for (var item : request.getDetalles()) {
            Product producto = productClient.getProduct(item.getProductoId());
            if (producto == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto con ID " + item.getProductoId() + " no encontrado"
                );
            }
            if (producto.getStock() < item.getCantidad()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Stock insuficiente para el producto: " + producto.getName()
                );
            }

            int nuevoStock = producto.getStock() - item.getCantidad();
            productClient.updateStock(
                    producto.getId(),
                    new StockUpdateRequest(nuevoStock)
            );

            PedidoDetalle det = new PedidoDetalle();
            det.setId(null);
            det.setProductoId(producto.getId());
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitario(BigDecimal.valueOf(producto.getPrice()));
            detalles.add(det);

            total = total.add(
                    BigDecimal.valueOf(producto.getPrice())
                            .multiply(BigDecimal.valueOf(item.getCantidad()))
            );
        }

        Pedido pedido = new Pedido();
        pedido.setId(null);
        pedido.setClienteId(request.getClienteId());
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("E");
        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }
}
