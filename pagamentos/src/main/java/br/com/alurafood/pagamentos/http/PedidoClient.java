package br.com.alurafood.pagamentos.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${ms-pedidos.url}")
public interface PedidoClient {

    @RequestMapping(method = RequestMethod.PATCH, value = "/pedidos/{id}/confirmar")
    void confirmarPedido(@PathVariable Long id);
}
