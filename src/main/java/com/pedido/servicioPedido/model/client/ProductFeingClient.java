package com.pedido.servicioPedido.model.client;

import com.pedido.servicioPedido.dto.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "servicioProducto", url = "http://localhost:8081")
public interface ProductFeingClient {

    @GetMapping("/api/products/{id}")
    ProductFallback getProduct(@PathVariable("id") Long id);

    @PatchMapping("/api/products/stock/{id}")
    void updateStock(
            @PathVariable("id") Long id,
            @RequestBody StockUpdateRequest request
    );
}
