package br.com.alurafood.pagamentos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private BigDecimal valor;
    @NotBlank
    @Size(max = 100)
    private String nome;
    @NotBlank
    @Size(max = 19)
    private String numero;
    @NotBlank
    @Size(max = 7)
    private String expiracao;
    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    private Long pedidoId;
    @NotNull
    private Long formaDePagamentoId;

    public Pagamento() {}

    public Pagamento(BigDecimal valor, String nome, String numero, String expiracao, String codigo, Long pedidoId, Long formaDePagamentoId) {
        this.valor = valor;
        this.nome = nome;
        this.numero = numero;
        this.expiracao = expiracao;
        this.codigo = codigo;
        this.status = Status.CRIADO;
        this.pedidoId = pedidoId;
        this.formaDePagamentoId = formaDePagamentoId;
    }

    public void update(Pagamento pagamento) {
        valor = pagamento.valor;
        nome = pagamento.nome;
        numero = pagamento.numero;
        expiracao = pagamento.expiracao;
        codigo = pagamento.codigo;
        status = pagamento.status;
        pedidoId = pagamento.pedidoId;
        formaDePagamentoId = pagamento.formaDePagamentoId;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public String getExpiracao() {
        return expiracao;
    }

    public String getCodigo() {
        return codigo;
    }

    public Status getStatus() {
        return status;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getFormaDePagamentoId() {
        return formaDePagamentoId;
    }
}
