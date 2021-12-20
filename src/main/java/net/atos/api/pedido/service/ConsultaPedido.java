package net.atos.api.pedido.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	
	public Page<PedidoVO> porPeriodo(@NotNull LocalDate inicio, LocalDate fim, Pageable pageable) {
		
		Page<PedidoEntity> pedidosEncontrados = pedidoRepository.findByDataEmissaoBetween(inicio, fim, pageable);
		
		if (pedidosEncontrados.isEmpty()) {
			throw new NotFoundException("Nenhum pedido encontrado no período informado");
		}
				
		return new PageImpl<>(
				pedidosEncontrados.stream()
				.map(PedidoFactory::new)
				.map(PedidoFactory::toVO)
				.collect(Collectors.toList()),
				pedidosEncontrados.getPageable(),
				pedidosEncontrados.getTotalElements());
	}
}
