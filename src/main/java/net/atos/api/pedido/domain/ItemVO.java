package net.atos.api.pedido.domain;

import javax.validation.constraints.NotNull;

import lombok.Data;
import net.atos.api.pedido.domain.ItemVO;

@Data
public class ItemVO {
	
	@NotNull(message = "Codigo do item não pode ser nulo")
	private Integer codigoItem;
	
	@NotNull(message = "Campo preço unitario não pode ser nulo")
	private Double precoUnitario;
	
}
