package com.pedido.servicioPedido.controller;

import com.pedido.servicioPedido.dto.PedidoRequest;
import com.pedido.servicioPedido.model.Pedido;
import com.pedido.servicioPedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Map<String,Object>> crearPedido(
            @RequestBody PedidoRequest request) {
        Pedido creado = pedidoService.crearPedido(request);
        Map<String,Object> resp = new HashMap<>();
        resp.put("message", "Pedido creado exitosamente");
        resp.put("pedidoId", creado.getId());
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    /** GET /api/pedidos → lista todos los pedidos */
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> lista = pedidoService.getAllPedidos();
        return ResponseEntity.ok(lista);
    }

    /** GET /api/pedidos/{id} → devuelve un pedido concreto */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable Long id) {
        Pedido p = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(p);
    }
}
