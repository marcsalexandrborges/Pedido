package net.atos.api.pedido.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.service.CriaPedido;


@RestController
@RequestMapping("/v1/pedido")
//@Tag(name = "Pedido")
public class PedidoController {

	private CriaPedido criaPedidoService;
	
	

	public PedidoController(CriaPedido criaService) {
		super();
		
		this.criaPedidoService = criaService;
		

	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	@Operation(description = "Cria um pedido")
	public ResponseEntity<PedidoVO> criaPedido(@Valid @RequestBody PedidoVO pedido) {

		PedidoVO createdPedido = criaPedidoService.criar(pedido);

		URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
				.buildAndExpand(createdPedido.getId()).toUri();

		return ResponseEntity.created(uri).body(createdPedido);
	}
	
}



