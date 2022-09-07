package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Pagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record PagamentoResponse(
        @NotNull Long id,
        @NotNull @Positive BigDecimal valor,
        @NotBlank @Size(max = 100) String nome,
        @NotNull Long pedidoId,
        @NotNull Long formaDePagamentoId) {

    public static PagamentoResponse toResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getPedidoId(),
                pagamento.getFormaDePagamentoId());
    }
}
