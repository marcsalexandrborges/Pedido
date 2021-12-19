package net.atos.api.pedido.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.factory.PedidoFactory;
import net.atos.api.pedido.repository.IPedidoRepository;
import net.atos.api.pedido.repository.PedidoEntity;

@Service
public class CriaPedido implements ICriaPedido {
	private Validator validator;
	
	private IPedidoRepository pedidoRepository;
	
	public CriaPedido(Validator validator, IPedidoRepository repository) {
		this.validator = validator;
		this.pedidoRepository = repository;
	}

	@Transactional
	public PedidoVO aprovar(@NotNull(message = "Pedido não pode ser nulo") PedidoVO pedido) {

		Set<ConstraintViolation<PedidoVO>> 
		validateMessages = this.validator.validate(pedido);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Pedido Inválido",validateMessages);
		}
		
		PedidoEntity pedidoEntity = new PedidoFactory(pedido).toEntity();

		pedidoRepository.save(pedidoEntity);		
		
		pedido.setId(pedidoEntity.getId());	
		
		return pedido; 

	
	}

}
	


