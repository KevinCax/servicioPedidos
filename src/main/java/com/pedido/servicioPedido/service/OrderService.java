
package com.pedido.servicioPedido.service;

import com.pedido.servicioPedido.dto.StockUpdateRequest;
import com.pedido.servicioPedido.model.Order;
import com.pedido.servicioPedido.model.OrderItem;
import com.pedido.servicioPedido.model.client.ProductFallback;
import com.pedido.servicioPedido.model.client.ProductFeingClient;
import com.pedido.servicioPedido.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductFeingClient productFeingClient;

    public Order createOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Long productId = item.getProductId();
            ProductFallback productFallback = productFeingClient.getProduct(productId);

            if (productFallback.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: " + productFallback.getName()
                );
            }

            int nuevoStock = productFallback.getStock() - item.getQuantity();
            productFeingClient.updateStock(
                    productId,
                    new StockUpdateRequest(nuevoStock)
            );
        }

        return orderRepository.save(order);
    }
}
