package net.atos.api.pedidofactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.factory.PedidoFactory;
import net.atos.api.pedido.repository.PedidoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PedidoFactoryTest {
	

	@Test
	@DisplayName("Testa o factory de VO para entity")
	public void testCriarVoToEntity() {
		PedidoVO pedido =  new PedidoVO();
		pedido.setIdOrcamento(1L);
		pedido.setStatus("Teste");
		pedido.setValor(10.0);
		pedido.setDataEmissao(LocalDate.now());	

		PedidoEntity orcEntity = 
				new PedidoFactory(pedido).toEntity();
		
		assertNotNull(orcEntity);
		assertNotNull(orcEntity.getDataEmissao());
		assertEquals(pedido.getIdOrcamento(),orcEntity.getIdOrcamento());
		assertEquals(pedido.getStatus(),orcEntity.getStatus());
		assertEquals(pedido.getValor(),orcEntity.getValor());
		assertEquals(pedido.getDataEmissao(),orcEntity.getDataEmissao());
		
		PedidoVO voCriado = 
				new PedidoFactory(orcEntity).toVO();

		assertNotNull(voCriado);
		assertNotNull(voCriado.getDataEmissao());
		assertEquals(pedido.getDataEmissao(),voCriado.getDataEmissao());
		assertEquals(pedido.getIdOrcamento(),voCriado.getIdOrcamento());
		assertEquals(pedido.getStatus(),voCriado.getStatus());
		assertEquals(pedido.getValor(),voCriado.getValor());
		
	}

}



