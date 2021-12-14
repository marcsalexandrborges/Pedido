package net.atos.api.pedido.repository;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class ItemPK implements Serializable{
	
private static final long serialVersionUID = -4076499057085848992L;
	
	@Column(name = "ID_ITEM")
	@NotNull(message = "Campo numero do Item não pode ser nulo")
	private Integer item;
	
	@ManyToOne
	@JoinColumn(name="ID_PEDIDO")
	private PedidoEntity pedido;
	
	@Override
	public int hashCode() {
		return Objects.hash(pedido, item);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPK other = (ItemPK) obj;
		return Objects.equals(pedido, other.pedido) && Objects.equals(item, other.item);
	}

}

	



