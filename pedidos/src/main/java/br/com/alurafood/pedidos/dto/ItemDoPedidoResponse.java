package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.ItemDoPedido;

public record ItemDoPedidoResponse(Long id, String descricao, Double quantidade, Double preco) {

    public static ItemDoPedidoResponse toResponse(ItemDoPedido itemDoPedido) {
        return new ItemDoPedidoResponse(itemDoPedido.getId(), itemDoPedido.getDescricao(),
                itemDoPedido.getQuantidade(), itemDoPedido.getPreco());
    }
}
