package net.atos.api.pedido.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PedidoVO {
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	
	@NotNull(message = "Campo id do orcamento não pode ser nulo")
	private Long idOrcamento;
	
	private Double valor;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDate dataEmissao;
	
	private String status;
	
}



