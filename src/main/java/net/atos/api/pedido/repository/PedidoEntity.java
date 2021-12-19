package net.atos.api.pedido.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_PEDIDO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PedidoEntity implements Serializable{
	

	private static final long serialVersionUID = -6306260709157057159L;
	
	@Id
	@Column(name = "ID_PEDIDO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_pedido")	
	@SequenceGenerator(name = "sq_pedido",sequenceName = "sequence_pedido", allocationSize = 1, initialValue = 1)
	private Long id;
	
	@NotNull(message="Campo id do orcamento não pode ser nulo")
	@Column(name = "ID_ORCAMENTO")
	private Long idOrcamento;
	
	@Column(name = "VALOR_ORCAMENTO")
	private Double valor;
	
	@Column(name = "DT_EMISSAO_ORCAMENTO")
	private LocalDate dataEmissao;
	
	@Column(name = "STATUS_ORCAMENTO")
	private String status;
	
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



