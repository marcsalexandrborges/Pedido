package net.atos.api.pedido.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class PedidoControllerIT {

private static final String URI_PEDIDO = "/v1/pedido";
	
	@Autowired
	private WebApplicationContext wac;
		
	private MockMvc mockMvc;
	
	@BeforeAll
	public void setup() {		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	
	}	

	@Test
	@DisplayName("Consulta pedido por data de emissao")
	public void testConsultaDataEmissao() throws Exception {
		
		String inicio = LocalDate.now().minusDays(1l).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String fim = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
		this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_PEDIDO
    					.concat("/{inicio}/{fim}"),
    					inicio, fim))
    					.andDo(print())
    					.andExpect(status().isOk());
		
	}

}
