package net.atos.api.pedidofactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.pedido.domain.ItemVO;
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
		pedido.setDataEmissao(LocalDate.now());	
		pedido.setQuantidade(1);
		pedido.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		pedido.add(item);

		PedidoEntity orcEntity = 
				new PedidoFactory(pedido).toEntity();
		
		assertNotNull(orcEntity);
		assertNotNull(orcEntity.getDataEmissao());
		assertEquals(pedido.getDataEmissao(),orcEntity.getDataEmissao());
		
		assertNotNull(orcEntity.getQuantidade());
		assertEquals(pedido.getQuantidade(),orcEntity.getQuantidade());
		
		assertNotNull(orcEntity.getValor());
		assertEquals(pedido.getValor(),orcEntity.getValor());
		
		assertNotNull(orcEntity.getItens());
		assertEquals(pedido.getItens().size(),orcEntity.getItens().size());
		assertEquals(pedido.getItens().get(0).getCodigoItem(),orcEntity.getItens().get(0).getCodigoItem());
		assertEquals(pedido.getItens().get(0).getPrecoUnitario(),orcEntity.getItens().get(0).getPrecoUnitario());
		
		PedidoVO voCriado = 
				new PedidoFactory(orcEntity).toVO();

		assertNotNull(voCriado);
		assertNotNull(voCriado.getDataEmissao());
		assertEquals(pedido.getDataEmissao(),voCriado.getDataEmissao());
		
		assertNotNull(voCriado.getQuantidade());
		assertEquals(pedido.getQuantidade(),voCriado.getQuantidade());
		
		assertNotNull(voCriado.getValor());
		assertEquals(pedido.getValor(),voCriado.getValor());
		
		assertNotNull(voCriado.getItens());
		assertEquals(pedido.getItens().size(),voCriado.getItens().size());
		assertEquals(pedido.getItens().get(0).getCodigoItem(),voCriado.getItens().get(0).getCodigoItem());
		assertEquals(pedido.getItens().get(0).getPrecoUnitario(),voCriado.getItens().get(0).getPrecoUnitario());
		
	}

}



