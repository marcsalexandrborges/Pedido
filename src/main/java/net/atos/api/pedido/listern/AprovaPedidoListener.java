package net.atos.api.pedido.listern;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import net.atos.api.pedido.domain.OrcamentoVO;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.service.CriaPedido;

@Component
public class AprovaPedidoListener {
	
	private CriaPedido criaPedido;
	
	public AprovaPedidoListener(CriaPedido pCriaPedido) {
		this.criaPedido = pCriaPedido;
	}
	
	@RabbitListener(queues = "cria-pedido")
	public void execute(OrcamentoVO orcamento) {
		
		PedidoVO pedido = new PedidoVO();
		pedido.setIdOrcamento(orcamento.getId());
		pedido.setDataEmissao(orcamento.getDataEmissao());
		pedido.setStatus(orcamento.getStatus());
		pedido.setValor(orcamento.getValor());
		
		this.criaPedido.aprovar(pedido);
		
	}

}