package net.atos.api.pedido.factory;

import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.repository.PedidoEntity;

public class PedidoFactory {
	
	private PedidoVO pedidoVO;
	private PedidoEntity pedidoEntity;
	
	public PedidoFactory(PedidoVO pedido) {
		this.pedidoEntity = this.transformaEntity(pedido);
		this.pedidoVO = pedido;	
	}
	
	public PedidoFactory(PedidoEntity pedidoEnt) {
		this.pedidoEntity = pedidoEnt;
		this.pedidoVO = this.transformaVO(pedidoEnt);		
	}

	private PedidoVO transformaVO(PedidoEntity pedidoEntity) {
		PedidoVO pedidoVO = new PedidoVO();
		pedidoVO.setId(pedidoEntity.getId());
		pedidoVO.setDataEmissao(pedidoEntity.getDataEmissao());
		pedidoVO.setIdOrcamento(pedidoEntity.getIdOrcamento());
		pedidoVO.setStatus(pedidoEntity.getStatus());
		pedidoVO.setValor(pedidoEntity.getValor());
		
		return pedidoVO;
	}
	
	private PedidoEntity transformaEntity(PedidoVO pedidoVO) {
		PedidoEntity pedidoEntity = new PedidoEntity();
		pedidoEntity.setId(pedidoVO.getId());
		pedidoEntity.setDataEmissao(pedidoVO.getDataEmissao());
		pedidoEntity.setIdOrcamento(pedidoVO.getIdOrcamento());
		pedidoEntity.setStatus(pedidoVO.getStatus());
		pedidoEntity.setValor(pedidoVO.getValor());
		
		return pedidoEntity;
	}
	
	public PedidoEntity toEntity() {		
		return this.pedidoEntity;
	}
	
	public PedidoVO toVO() {
		
		return this.pedidoVO;
	}

}



