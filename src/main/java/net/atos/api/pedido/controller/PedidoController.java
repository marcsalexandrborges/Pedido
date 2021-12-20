package net.atos.api.pedido.controller;

import java.time.LocalDate;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.pedido.config.PageableBinding;
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
	
	@PageableBinding
	@GetMapping("/{inicio}/{fim}")
	@Operation(description = "Lista todos os pedidos")
	public ResponseEntity<Page<PedidoVO>> buscaPedidoPorEmissao(
			@PathVariable("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicial,
			@PathVariable("fim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFinal,
			@ParameterObject @PageableDefault(sort = {"dataEmissao"},
				direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		
		Page<PedidoVO> pedidosEncontrados = consultaPedido.porPeriodo(dataInicial, dataFinal, pageable);
		
		return ResponseEntity.ok(pedidosEncontrados);

	}
	
}



