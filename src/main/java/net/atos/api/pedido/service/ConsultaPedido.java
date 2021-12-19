package net.atos.api.pedido.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.factory.PedidoFactory;
import net.atos.api.pedido.repository.IPedidoRepository;
import net.atos.api.pedido.repository.PedidoEntity;

@Service
public class ConsultaPedido {
	
	private Validator validator;
	
	private IPedidoRepository pedidoRepository;
	
	public ConsultaPedido(Validator validator, IPedidoRepository repository) {
		this.validator = validator;
		this.pedidoRepository = repository;
	}
	
	public PedidoVO porId(long id) {
		PedidoEntity pedidoEntity = this.pedidoRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Pedido não encontrado"));
		
		return new PedidoFactory(pedidoEntity).toVO();
	}
}
