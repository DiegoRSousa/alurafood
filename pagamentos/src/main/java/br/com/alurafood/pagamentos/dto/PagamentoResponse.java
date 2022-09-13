package br.com.alurafood.pagamentos.dto;

import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;

import java.math.BigDecimal;

public record PagamentoResponse(
        Long id,
        BigDecimal valor,
        String nome,
        Status staus,
        Long pedidoId,
        Long formaDePagamentoId) {

    public static PagamentoResponse toResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getStatus(),
                pagamento.getPedidoId(),
                pagamento.getFormaDePagamentoId());
    }
}
