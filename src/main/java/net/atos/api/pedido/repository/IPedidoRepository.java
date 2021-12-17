package net.atos.api.pedido.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedidoRepository extends PagingAndSortingRepository<PedidoEntity, Long> {
	

}
