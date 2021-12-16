package net.atos.api.pedido.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import net.atos.api.pedido.domain.ItemVO;

@Data
public class ItemVO {
	
	@NotNull(message = "Codigo do item não pode ser nulo")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer codigoItem;
	
	@NotNull(message = "Campo preço unitario não pode ser nulo")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double precoUnitario;
	
	@NotNull(message = "Campo descricao não pode ser nulo")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String descricao;
	
	@NotNull(message = "Campo quantidade não pode ser nulo")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer quantidade;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double valorItens;

	
}
