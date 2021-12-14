package net.atos.api.pedido.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.pedido.config.PageableBinding;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.service.BuscaPedido;
import net.atos.api.pedido.service.CriaPedido;


@RestController
@RequestMapping("/v1/pedido")
//@Tag(name = "Pedido")
public class PedidoController {

	private CriaPedido criaPedidoService;
	
	private BuscaPedido buscaPedidoService;

	public PedidoController(CriaPedido criaService, BuscaPedido buscaService) {
		super();
		
		this.criaPedidoService = criaService;
		this.buscaPedidoService = buscaService;

	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um pedido")
	public ResponseEntity<PedidoVO> criaPedido(@Valid @RequestBody PedidoVO pedido) {

		PedidoVO createdPedido = criaPedidoService.criar(pedido);

		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(createdPedido.getId()).toUri();

		return ResponseEntity.created(uri).body(createdPedido);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta uma pedido por id")
	public ResponseEntity<PedidoVO> getPedidoPorId(@PathVariable("id") Long id) {

		PedidoVO pedidoEncontrado = buscaPedidoService.porId(id);

		return ResponseEntity.ok(pedidoEncontrado);
	}
	
	@PageableBinding
	@GetMapping(value = "/emissao-periodos/{dataEmissao}/{dataFim}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta pedido por período")
	public ResponseEntity<Page<PedidoVO>> getPedidosPorPeriodo(
			@PathVariable("dataEmissao") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataEmissao,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim,
			@ParameterObject @PageableDefault(sort = {
					"dataEmissao" }, direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {

		Page<PedidoVO> pedidosEncontradas = this.buscaPedidoService.porPeriodoDataEmissao(dataEmissao,
				dataFim, pageable);

		return ResponseEntity.ok(pedidosEncontradas);

	}
}



