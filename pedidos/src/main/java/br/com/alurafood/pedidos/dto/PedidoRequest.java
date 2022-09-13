package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Pedido;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public record PedidoRequest(@NotNull @Valid List<ItemDoPedidoRequest> itens) {
    public Pedido toModel() {
        return new Pedido(itens);
    }
}
