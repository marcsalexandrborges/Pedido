package net.atos.api.pedido.factory;

import java.util.concurrent.atomic.AtomicInteger;

import net.atos.api.pedido.domain.ItemVO;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.repository.ItemEntity;
import net.atos.api.pedido.repository.ItemPK;
import net.atos.api.pedido.repository.PedidoEntity;

public class PedidoFactory {
	
	private PedidoVO pedidoVO;
	private PedidoEntity pedidoEntity;
	
	public PedidoFactory(PedidoVO pedido) {
		this.pedidoEntity = this.transformaEntity(pedido);
		this.pedidoVO = pedido;	
	}
	
	public PedidoFactory(PedidoEntity orcEntity) {
		this.pedidoEntity = orcEntity;
		this.pedidoVO = this.transformaVO(orcEntity);		
	}

	private PedidoVO transformaVO(PedidoEntity orcEntity) {
		PedidoVO orcVO = new PedidoVO();
		orcVO.setDataEmissao(orcEntity.getDataEmissao());
		orcVO.setValor(orcEntity.getValor());
		
		AtomicInteger numeroItem = new AtomicInteger(); 
		orcEntity.getItens().stream().forEach(item -> 
				this.construirItemVO(orcVO, numeroItem, item));
		
		return orcVO;
	}

	private void construirItemVO(PedidoVO orcVO, AtomicInteger numeroItem, ItemEntity item) {
		ItemVO itemVO = new ItemVO();
		itemVO.setCodigoItem(item.getCodigoItem());
		itemVO.setPrecoUnitario(item.getPrecoUnitario());
		itemVO.setDescricao(item.getDescricao());
		itemVO.setQuantidade(item.getQuantidade());
		itemVO.setValorItens(item.getValorItens());
		orcVO.add(itemVO);
	}

	private PedidoEntity transformaEntity(PedidoVO pedido) {
		PedidoEntity orcEntity = new PedidoEntity();
		orcEntity.setDataEmissao(pedido.getDataEmissao());
		orcEntity.setValor(pedido.getValor());
		
		AtomicInteger numeroItem = new AtomicInteger(); 
		pedido.getItens().stream().forEach(item -> 
				this.construirItemEntity(orcEntity, numeroItem, item));
		
		return orcEntity;
	}

	private void construirItemEntity(PedidoEntity orcEntity, AtomicInteger numeroItem, ItemVO item) {
		ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(new ItemPK());
		itemEntity.getId().setItem(numeroItem.incrementAndGet());
		itemEntity.getId().setPedido(orcEntity);
		itemEntity.setCodigoItem(item.getCodigoItem());
		itemEntity.setPrecoUnitario(item.getPrecoUnitario());
		itemEntity.setDescricao(item.getDescricao());
		itemEntity.setQuantidade(item.getQuantidade());
		itemEntity.setValorItens(item.getValorItens());
		
		orcEntity.add(itemEntity);
	}
	
	public PedidoEntity toEntity() {		
		return this.pedidoEntity;
	}
	
	public PedidoVO toVO() {
		
		return this.pedidoVO;
	}

}



