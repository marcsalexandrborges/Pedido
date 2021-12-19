package net.atos.api.pedido.service;

import javax.validation.constraints.NotNull;

import net.atos.api.pedido.domain.PedidoVO;

public interface ICriaPedido {
	
	public PedidoVO aprovar(@NotNull(message = "Pedido não pode ser nulo") PedidoVO pedido);

}



