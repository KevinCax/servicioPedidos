package com.pedido.servicioPedido.model.client;

import com.pedido.servicioPedido.dto.StockUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFallback implements ProductFeingClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer stock;


    @Override
    public ProductFallback getProduct(Long id) {
        return null;
    }

    @Override
    public void updateStock(Long id, StockUpdateRequest request) {

    }
}
