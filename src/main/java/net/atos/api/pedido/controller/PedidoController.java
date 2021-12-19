package net.atos.api.pedido.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.service.ConsultaPedido;


@RestController
@RequestMapping("/v1/pedido")
//@Tag(name = "Pedido")
public class PedidoController {

	private ConsultaPedido consultaPedido;

	public PedidoController(ConsultaPedido buscaPedido) {
		super();		
		this.consultaPedido = buscaPedido;	
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(description = "Consulta um pedido por id")
	public ResponseEntity<PedidoVO> getPedidoPorId(@PathVariable("id") Long id) {

		PedidoVO pedidoEncontrado = consultaPedido.porId(id);

		return ResponseEntity.ok(pedidoEncontrado);
	}
	
}



