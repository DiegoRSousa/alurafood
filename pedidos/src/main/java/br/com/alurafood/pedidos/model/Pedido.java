package br.com.alurafood.pedidos.model;

import br.com.alurafood.pedidos.dto.ItemDoPedidoRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime instante;

    @NotNull @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pedido_id")
    private List<ItemDoPedido> itens = new ArrayList<>();

    public Pedido() {
        this.instante = LocalDateTime.now();
        this.status = Status.REALIZADO;
    }

    public Pedido(List<ItemDoPedidoRequest> itens) {
        this.itens = itens.stream().map(ItemDoPedidoRequest::toModel).toList();
        this.instante = LocalDateTime.now();
        this.status = Status.REALIZADO;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ItemDoPedido> getItens() {
        return itens;
    }
}
