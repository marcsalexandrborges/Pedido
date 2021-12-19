package net.atos.api.pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.repository.IPedidoRepository;
import net.atos.api.pedido.repository.PedidoEntity;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ConsultaPedidoTest {

	private ConsultaPedido buscaPedidoService;
	
	private Validator validator;
	
	private IPedidoRepository pedidoRepository;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();	
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.pedidoRepository = Mockito.mock(IPedidoRepository.class);
		buscaPedidoService = new ConsultaPedido(validator, pedidoRepository);	
	}	
	
	@Test	
	@DisplayName("Testa quando não encontra um orcamento por ID.")
	public void testBuscaOrcamentoIdError(){
		assertNotNull(this.buscaPedidoService);
		var assertThrows = assertThrows(NotFoundException.class, ()->
			this.buscaPedidoService.porId(1l));
		
		then(pedidoRepository).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Pedido não encontrado");
		
	}
	
	
	@Test	
	@DisplayName("Testa quando encontra um orcamento por ID.")
	public void testBuscaOrcamentoId(){
		assertNotNull(this.buscaPedidoService);
		
		PedidoEntity pedidoTreinado = new PedidoEntity();
		pedidoTreinado.setId(2l);
		pedidoTreinado.setDataEmissao(LocalDate.now());
		
		when(pedidoRepository.findById(anyLong()))
				.thenReturn(Optional.of(pedidoTreinado));
		
		PedidoVO pedidoRetornado = this.buscaPedidoService.porId(2l);
		
		then(pedidoRepository).should(times(1)).findById(anyLong());
		
		assertNotNull(pedidoRetornado);
		assertEquals(2l, pedidoRetornado.getId());
		
		
	}

}
