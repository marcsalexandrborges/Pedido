package net.atos.api.pedido.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private List<ItemPedidoVO> itens;
	
	private Double valor;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataEmissao;
	
	private String status;
	
	public void add(ItemPedidoVO item) {
		List<ItemPedidoVO> itensOrcamento = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensOrcamento.add(item);
		
		this.itens = itensOrcamento; 
	}

}
