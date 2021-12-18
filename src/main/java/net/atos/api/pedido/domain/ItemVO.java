package net.atos.api.pedido.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemVO {
	private Integer codigoItem;
	
	private Double precoUnitario;
	
	private String descricao;
	
	private Integer quantidade;
	
	private Double valorItens;
}
