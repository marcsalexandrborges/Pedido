package net.atos.api.pedido.listern;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import net.atos.api.pedido.domain.ItemPedidoVO;
import net.atos.api.pedido.domain.ItemVO;
import net.atos.api.pedido.domain.OrcamentoVO;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.service.CriaPedido;

@Component
public class CriaPedidoListern {
	
	private CriaPedido criaPedido;
	
	public CriaPedidoListern(CriaPedido pCriaPedido) {
		this.criaPedido = pCriaPedido;
	}
	
	@RabbitListener(queues = "cria-pedido")
	public void execute(OrcamentoVO orcamento, ItemVO item) {
		
		PedidoVO pedido = new PedidoVO();
		pedido.setId(orcamento.getId());
		pedido.setDataEmissao(orcamento.getDataEmissao());
		pedido.setValor(orcamento.getValor());
		
		ItemPedidoVO itemPedido = new ItemPedidoVO();
		itemPedido.setCodigoItem(item.getCodigoItem());
		itemPedido.setDescricao(item.getDescricao());
		itemPedido.setPrecoUnitario(item.getPrecoUnitario());
		itemPedido.setQuantidade(item.getQuantidade());
		itemPedido.setValorItens(item.getValorItens());
		
		pedido.add(itemPedido);
		
		this.criaPedido.criar(pedido);
		
	}

}