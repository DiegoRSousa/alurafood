package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoRequest;
import br.com.alurafood.pedidos.dto.PedidoResponse;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.PedidoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/pedidos")
public class PedidoController {

    private static Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoRepository pedidoRepository;

    private final Counter pedidoEncontrado;
    private final Counter pedidoNaoEncontrado;

    public PedidoController(PedidoRepository pedidoRepository, MeterRegistry meterRegistry) {
        this.pedidoRepository = pedidoRepository;
        pedidoEncontrado = Counter.builder("pedido_encontrado")
                .description("pedidos encontrados")
                .register(meterRegistry);
        pedidoNaoEncontrado = Counter.builder("pedido_nao_encontrado")
                .description("pedidos não encontrados")
                .register(meterRegistry);
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid PedidoRequest pedidoRequest,
                                    UriComponentsBuilder uriBuilder) {
        var pedido = pedidoRequest.toModel();
        pedidoRepository.save(pedido);
        URI uri = uriBuilder.path("pagamentos/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> listar(@PageableDefault(size = 3) Pageable pageable) {
        logger.info("buscando por todos os registros");
        var pedidos = pedidoRepository.findAll(pageable);

        return ResponseEntity.ok(pedidos.map(PedidoResponse::new));
    }


    @GetMapping("{id}")
    public ResponseEntity<PedidoResponse> detalhar(@PathVariable @NotNull Long id) {

        var pedido = buscarPedidoPorId(id);

        return ResponseEntity.ok(new PedidoResponse(pedido));
    }


    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPedido(@PathVariable @NotNull Long id) {

        var pedido = buscarPedidoPorId(id);
        pedido.setStatus(Status.CONFIRMADO);
        pedidoRepository.save(pedido);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/porta")
    public String retornaPorta(@Value("${local.server.port}") String porta) {
        return String.format("requisicao respondida pela instância executando na porta %s", porta);
    }

    private Pedido buscarPedidoPorId(Long id) {
        var pedidos = pedidoRepository.findById(id).orElseThrow(() -> {
            pedidoNaoEncontrado.increment();
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "pedido não encontrado!");
        });
        pedidoEncontrado.increment();
        return pedidos;
    }
}
