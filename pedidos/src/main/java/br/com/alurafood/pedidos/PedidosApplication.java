package br.com.alurafood.pedidos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PedidosApplication {

	private static Logger logger = LoggerFactory.getLogger(PedidosApplication.class);
	public static void main(String[] args) {
		logger.info("Iniciando a aplicação de pedidos");
		SpringApplication.run(PedidosApplication.class, args);
		logger.info("Aplicação de cadastro de pedidos");
	}

}
