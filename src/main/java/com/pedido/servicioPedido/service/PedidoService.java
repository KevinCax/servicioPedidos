package com.pedido.servicioPedido.service;

import com.pedido.servicioPedido.dto.PedidoRequest;
import com.pedido.servicioPedido.dto.StockUpdateRequest;
import com.pedido.servicioPedido.model.Pedido;
import com.pedido.servicioPedido.model.PedidoDetalle;
import com.pedido.servicioPedido.model.client.ProductFallback;
import com.pedido.servicioPedido.model.client.ProductFeingClient;
import com.pedido.servicioPedido.repository.PedidoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductFeingClient productFeingClient;

    public Pedido crearPedido(PedidoRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<PedidoDetalle> detalles = new ArrayList<>();

        for (var item : request.getDetalles()) {
            log.debug("✅ Fetching product with id = {}", item.getProductoId());
            ProductFallback producto;
            try {
                producto = productFeingClient.getProduct(item.getProductoId());
            } catch (FeignException.NotFound ex) {
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
            try {
                productFeingClient.updateStock(
                        producto.getId(),
                        new StockUpdateRequest(nuevoStock)
                );
            } catch (FeignException ex) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Error actualizando stock para el producto con ID: " + producto.getId()
                );
            }

            // Construir detalle del pedido
            PedidoDetalle det = new PedidoDetalle();
            det.setProductoId(producto.getId());
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitario(BigDecimal.valueOf(producto.getPrice()));
            detalles.add(det);

            // Sumar al total
            total = total.add(
                    BigDecimal.valueOf(producto.getPrice())
                            .multiply(BigDecimal.valueOf(item.getCantidad()))
            );
        }

        // Crear y guardar Pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(request.getClienteId());
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("E");
        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    /** Devuelve un pedido por su ID, o lanza 404 si no existe */
    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pedido con ID " + id + " no encontrado")
                );
    }
}
