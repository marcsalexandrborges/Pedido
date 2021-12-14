package net.atos.api.pedido.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.pedido.domain.ItemVO;
import net.atos.api.pedido.domain.PedidoVO;
import net.atos.api.pedido.repository.IPedidoRepository;
	

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CriaPedidoTest {
	
	private CriaPedido criaPedido;

	private Validator validator;
	
	private IPedidoRepository pedidoRepository;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();

		this.validator = validatorFactor.getValidator();
	}

	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.pedidoRepository = Mockito.mock(IPedidoRepository.class);
		
		criaPedido = new CriaPedido(validator, pedidoRepository);	
	}
	
	@Test
	@DisplayName("Testa quando o pedido é nulo")
	void test_quando_pedido_Eh_Null_LancarExcecao() {

		assertNotNull(criaPedido);

		PedidoVO pedido =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							criaPedido.criar(pedido));
		
		assertNotNull(assertThrows);
		
	}

	@Test
	@DisplayName("Testa os campos obrigatorios do pedido.")
	void testCamposObrigatorios() {

		assertNotNull(criaPedido);

		PedidoVO pedido =  new PedidoVO();

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							criaPedido.criar(pedido));
		
		assertEquals(4, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());


		assertThat(mensagens, hasItems("Campo pedido não pode ser nulo",
				"Campo quantidade pedido não pode ser nulo",
				"Campo valor do pedido não pode ser nulo",
				"Campo data de emissão do pedido não pode ser nula"
				));
		
	}
	
	@Test	
	@DisplayName("Testa obrigatoriedade do campo dos itens.")
	public void testCamposObrigatoriosItens() {
		assertNotNull(criaPedido);

		PedidoVO pedido =  new PedidoVO();
		pedido.setDataEmissao(LocalDate.now());
		pedido.setId(1L);
		pedido.setQuantidade(1);
		pedido.setValor(BigDecimal.ONE);
		
		ItemVO item = new ItemVO();
		pedido.add(item);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
			criaPedido.criar(pedido));

		assertEquals(2, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Codigo do item não pode ser nulo",
				"Campo preço do pedido não pode ser nulo"
				));
		
	}
	
	@Test	
	@DisplayName("Testa data de emissão do pedido não diferente do dia atual.")
	public void testDTDiferenteAtual() {
		assertNotNull(criaPedido);

		PedidoVO pedido =  new PedidoVO();
		pedido.setDataEmissao(LocalDate.now().minusDays(1l));		
		pedido.setId(1L);
		pedido.setQuantidade(1);
		pedido.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		pedido.add(item);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
			criaPedido.criar(pedido));

		
		assertEquals(assertThrows.getMessage(),"A data de emissão do pedido deve ser atual.");
		
			
	}
	
	@Test	
	@DisplayName("Testa a criação do pedido.")
	public void testCriaPedido() {
		assertNotNull(criaPedido);	
		
		PedidoVO pedido =  new PedidoVO();
		pedido.setDataEmissao(LocalDate.now());		
		pedido.setId(1L);
		pedido.setQuantidade(1);
		pedido.setValor(BigDecimal.ONE);
		
			
		ItemVO item = new ItemVO();
		item.setCodigoItem(1);
		item.setPrecoUnitario(10.0);
		pedido.add(item);
		
		criaPedido.criar(pedido);
		
		then(pedidoRepository).should(times(1)).save(any());
		
	}


}



