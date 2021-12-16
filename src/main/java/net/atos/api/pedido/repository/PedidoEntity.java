package net.atos.api.pedido.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_PEDIDO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="OP_PEDIDO", 
//discriminatorType = DiscriminatorType.STRING)
public class PedidoEntity implements Serializable{
	

	private static final long serialVersionUID = -6306260709157057159L;
	
	@Id
	@Column(name = "ID_PEDIDO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_pedido")	
	@SequenceGenerator(name = "sq_pedido",sequenceName = "sequence_pedido", allocationSize = 1, initialValue = 1)
	private Long id;

	@NotNull(message = "Campo item não pode ser nulo")
	@Size(min = 1, message = "Campo item não pode ser nulo")
	@Valid
	@OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
	private List<ItemEntity> itens;
	
	@Column(name = "QTD_ITENS")
	@NotNull(message = "Campo quantidade não pode ser nulo")
	@Positive
	private Integer quantidade;
	
	@Column(name = "VALOR_PEDIDO")
	@NotNull(message="Campo valor do pedido não pode ser nulo")
	@Positive
	private Double valor;
	
	@Column(name = "DT_EMISSAO")
	@NotNull(message = "Campo data de emissão não pode ser nula")
	private LocalDate dataEmissao;
	
	public void add(ItemEntity item) {
		List<ItemEntity> itensPedido = 
				Optional.ofNullable(this.getItens()).orElseGet(()->new ArrayList());		
		itensPedido.add(item);
		
		this.itens = itensPedido; 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoEntity other = (PedidoEntity) obj;
		return Objects.equals(id, other.id);
	}

}



