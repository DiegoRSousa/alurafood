package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoRequest;
import br.com.alurafood.pagamentos.dto.PagamentoResponse;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoClient pedidoClient;

    public PagamentoController(PagamentoRepository pagamentoRepository, PedidoClient pedidoClient) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoClient = pedidoClient;
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> listar(@PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(pagamentoRepository.findAll(pageable).map(PagamentoResponse::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> detalhar(@PathVariable @NotNull Long id) {

        var pagamento = pagamentoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(PagamentoResponse.toResponse(pagamento));
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> salvar(@RequestBody @Valid PagamentoRequest pagamentoRequest,
                                                  UriComponentsBuilder uriBuilder) {
        var pagamento = pagamentoRequest.toModel();

        pagamentoRepository.save(pagamento);
        URI endereco = uriBuilder.path("pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(PagamentoResponse.toResponse(pagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponse> atualizar(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid PagamentoRequest pagamentoRequest) {

        var pagamento = pagamentoRepository.findById(id).orElseThrow(()
            -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        pagamento.update(pagamentoRequest.toModel());
        pagamentoRepository.save(pagamento);

        return ResponseEntity.ok(PagamentoResponse.toResponse(pagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @NotNull Long id) {

        if(pagamentoRepository.findById(id).isPresent())
            pagamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarPagamento(@PathVariable @NotNull Long id) {

        var pagamento = pagamentoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "pagamento não encontrado!"));

        pagamento.setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento);

        pedidoClient.confirmarPedido(pagamento.getPedidoId());

        return ResponseEntity.ok().build();
    }
}
