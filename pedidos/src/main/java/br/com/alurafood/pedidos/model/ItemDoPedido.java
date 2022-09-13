package br.com.alurafood.pedidos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "itensDoPedido")
public class ItemDoPedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descricao;

    @NotNull
    @Positive
    private Double quantidade;

    @NotNull
    @Positive
    private Double preco;

    @ManyToOne(optional = false)
    private Pedido pedido;

    public ItemDoPedido() {}
    public ItemDoPedido(String descricao, Double preco, Double quantidade) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
