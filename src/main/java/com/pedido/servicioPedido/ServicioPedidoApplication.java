package com.pedido.servicioPedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class ServicioPedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioPedidoApplication.class, args);

	}
}
