package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {

    private Long id;
    private LocalDateTime instante;
    private Status status;
    private List<ItemDoPedidoResponse> itens;

    public PedidoResponse() {}

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.instante = pedido.getInstante();
        this.status = pedido.getStatus();
        this.itens = pedido.getItens().stream().map(ItemDoPedidoResponse::toResponse).toList();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Status getStatus() {
        return status;
    }

    public List<ItemDoPedidoResponse> getItens() {
        return itens;
    }
}
