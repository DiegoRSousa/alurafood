package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoRequest;
import br.com.alurafood.pagamentos.dto.PagamentoResponse;
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

    public PagamentoController(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> findAll(@PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(pagamentoRepository.findAll(pageable).map(PagamentoResponse::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> findById(@PathVariable @NotNull Long id) {

        var pagamento = pagamentoRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(PagamentoResponse.toResponse(pagamento));
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> save(@RequestBody @Valid PagamentoRequest pagamentoRequest,
                                                  UriComponentsBuilder uriBuilder) {
        var pagamento = pagamentoRequest.toModel();

        pagamentoRepository.save(pagamento);
        URI endereco = uriBuilder.path("pagamentos/{id}").buildAndExpand(pagamento.getId()).toUri();

        return ResponseEntity.created(endereco).body(PagamentoResponse.toResponse(pagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponse> update(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid PagamentoRequest pagamentoRequest) {

        var pagamento = pagamentoRepository.findById(id).orElseThrow(()
            -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        pagamento.update(pagamentoRequest.toModel());
        pagamentoRepository.save(pagamento);

        return ResponseEntity.ok(PagamentoResponse.toResponse(pagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {

        if(pagamentoRepository.findById(id).isPresent())
            pagamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
