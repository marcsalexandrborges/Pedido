package net.atos.api.pedido.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	
	
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CriaPedidoTest {
	
	private CriaPedido aprovaPedido;

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
		
		aprovaPedido = new CriaPedido(validator, pedidoRepository);	
	}
	
	@Test
	@DisplayName("Testa quando o pedido é nulo")
	void test_quando_pedido_Eh_Null_LancarExcecao() {

		assertNotNull(aprovaPedido);

		PedidoVO pedido =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							aprovaPedido.aprovar(pedido));
		assertNotNull(assertThrows);
		
	}

	@Test
	@DisplayName("Testa os campos obrigatorios do pedido.")
	void testCamposObrigatorios() {

		assertNotNull(aprovaPedido);

		PedidoVO pedido = new PedidoVO();

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							aprovaPedido.aprovar(pedido));
		
		assertEquals(1, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
		     .stream()
		     .map(ConstraintViolation::getMessage)
		     .collect(Collectors.toList());


		assertThat(mensagens, hasItems("Campo id do orcamento não pode ser nulo"));
		
	}
	
	@Test	
	@DisplayName("Testa a criação do pedido.")
	public void testCriaPedido() {
		assertNotNull(aprovaPedido);	
		
		PedidoVO pedido =  new PedidoVO();
		pedido.setDataEmissao(LocalDate.now());		
		pedido.setId(1L);
		pedido.setIdOrcamento(1L);
		pedido.setValor(10.0);
		pedido.setStatus("Valido");
		
		aprovaPedido.aprovar(pedido);
		
		then(pedidoRepository).should(times(1)).save(any());
		
	}


}



