
package com.pedido.servicioPedido.service;

import com.pedido.servicioPedido.dto.StockUpdateRequest;
import com.pedido.servicioPedido.model.Order;
import com.pedido.servicioPedido.model.OrderItem;
import com.pedido.servicioPedido.model.client.Product;
import com.pedido.servicioPedido.model.client.ProductClient;
import com.pedido.servicioPedido.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Order createOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Long productId = item.getProductId();
            Product product = productClient.getProduct(productId);

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: " + product.getName()
                );
            }

            int nuevoStock = product.getStock() - item.getQuantity();
            productClient.updateStock(
                    productId,
                    new StockUpdateRequest(nuevoStock)
            );
        }

        return orderRepository.save(order);
    }
}
