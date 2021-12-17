package net.atos.api.pedido.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PedidoVO {
	
	private Long id;

	@NotNull(message = "Campo item não pode ser nulo")
	@Size(min = 1, message = "Campo item não pode ser nulo")
	@Valid
	private List<ItemVO> itens;
		
	@Positive
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Double valor;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDate dataEmissao;
	
	public void add(ItemVO item) {
		List<ItemVO> itensPedido = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensPedido.add(item);
		
		this.itens = itensPedido; 
	}

}



