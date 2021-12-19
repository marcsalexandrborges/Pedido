package net.atos.api.pedido.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrcamentoVO {
	
	private Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissao;
	
	private Double valor;
	
	private String status;

}
