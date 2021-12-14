package net.atos.api.pedido.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.validation.Validator;
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
public class BuscaPedido {
	
private Validator validator;
	
	private IPedidoRepository pedidoRepository;
	
	public BuscaPedido(Validator validator, IPedidoRepository repository) {
		this.validator = validator;
		this.pedidoRepository = repository;
	}
		
	public PedidoVO porId(long id) {
		PedidoEntity pedidoEntity = this.pedidoRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Pedido não encontrado com o id: = "+id));
		
		return new PedidoFactory(pedidoEntity).toVO();
	}
	
	public Page<PedidoVO>  porPeriodoDataEmissao(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
			
			Page<PedidoEntity> pedidoEntity = 
					     pedidoRepository.findByDataEmissaoBetween(dataInicio,dataFim, pageable);
			
			if(pedidoEntity.isEmpty()) {
				throw new NotFoundException("Nenhum pedido para o periodo informado");	
			}
			
			
			return new PageImpl<>(pedidoEntity.getContent().stream()
					.map(PedidoFactory::new)
					.map(PedidoFactory::toVO)
					.collect(Collectors.toList()),
					pedidoEntity.getPageable(),
					pedidoEntity.getTotalElements());		     
			
			 
			
		}

}


