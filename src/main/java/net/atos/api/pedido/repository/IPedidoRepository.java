package net.atos.api.pedido.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends PagingAndSortingRepository<PedidoEntity, Long> {
	
	public Page<PedidoEntity> findByDataEmissaoBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

}
