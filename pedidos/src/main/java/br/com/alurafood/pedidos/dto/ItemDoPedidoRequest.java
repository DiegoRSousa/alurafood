package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.ItemDoPedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ItemDoPedidoRequest(
        @NotNull @Positive Double quantidade,
        @NotNull @Positive Double preco,
        @NotBlank String descricao) {

    public ItemDoPedido toModel() {
        return new ItemDoPedido(descricao, preco, quantidade);
    }
}
