package net.atos.api.pedido.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.pedido.controller.page.PaginatedResponse;
import net.atos.api.pedido.domain.ItemVO;
import net.atos.api.pedido.domain.PedidoVO;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class PedidoControllerIT {
	
	private static final String URI_PEDIDO = "/v1/pedido";

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Autowired
	private EntityManager entityManager;

	@BeforeAll
	public void setup() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		assertNotNull(this.entityManager);

	}

	@Test
	@DisplayName("Envio do pedido sem os campos obrigatórios")
	public void testEnvioSemDados() throws Exception {
			PedidoVO pedido = new PedidoVO();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(URI_PEDIDO)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(pedido)))
				.andDo(print())
				.andExpect(status().isBadRequest());

	}

	@Test    
    @DisplayName("Cria Pedido")
    public void testPedidoCriado() throws Exception {
    	PedidoVO pedido =  new PedidoVO();
		pedido.setValor(10.0);
		pedido.setDataEmissao(LocalDate.now());
		
		ItemVO item = new ItemVO();
		item.setCodigoItem(45);
		item.setPrecoUnitario(3.5);
		item.setQuantidade(8);
		item.setDescricao("Coca");
		item.setValorItens(10.0);
		pedido.add(item);
    	
    	ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_PEDIDO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pedido))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	PedidoVO pedidoCriado = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						PedidoVO.class);
    	
    	ResultActions resultConsulted = this.mockMvc.perform(
			MockMvcRequestBuilders.get(URI_PEDIDO.concat("/{id}"),
					pedidoCriado.getId()))
					.andDo(print())
					.andExpect(status().isOk());
    	
    	PedidoVO pedidoConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				PedidoVO.class);
    	
    	assertEquals(1,pedidoConsultado.getItens().size());
    	
	
    }
	
}





