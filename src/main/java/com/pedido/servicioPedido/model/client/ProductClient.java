package com.pedido.servicioPedido.model.client;

import com.pedido.servicioPedido.dto.StockUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    Product getProduct(@PathVariable("id") Long id);

    @PatchMapping("/api/products/stock/{id}")
    void updateStock(
            @PathVariable("id") Long id,
            @RequestBody StockUpdateRequest request
    );
}
