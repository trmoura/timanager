package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "transferencia_detalhe")
public class TransferenciaDetalhe implements BaseEntity, Serializable {

	private static final long serialVersionUID = 8288475049350581444L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_peca_transferencia", nullable = false)
	private Peca pecaTranferencia;

	@NotNull
	@Column(name = "quantidade", precision = 15, scale = 4)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@NotNull
	@Column(name = "quantidade_atual_dia", precision = 15, scale = 4)
	private BigDecimal quantidadeAtualDia = BigDecimal.ZERO;

	@NotNull
	@Column(name = "total_quantidade", precision = 15, scale = 4)
	private BigDecimal totalQuantidade = BigDecimal.ZERO;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "transferencia_id", nullable = false)
	private Transferencia transferencia;

	@Override
	public Long getId() {
		return this.id;
	}

	public Peca getPecaTranferencia() {
		return pecaTranferencia;
	}

	public void setPecaTranferencia(Peca pecaTranferencia) {
		this.pecaTranferencia = pecaTranferencia;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferenciaDetalhe other = (TransferenciaDetalhe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public BigDecimal getQuantidadeAtualDia() {
		return quantidadeAtualDia;
	}

	public void setQuantidadeAtualDia(BigDecimal quantidadeAtualDia) {
		this.quantidadeAtualDia = quantidadeAtualDia;
	}

	@Transient
	public boolean isQuantidadeTransferenciaOk() {
		return this.quantidade.compareTo(quantidadeAtualDia) <= 0;
	}

	public BigDecimal getTotalQuantidade() {
		return totalQuantidade;
	}

	public void setTotalQuantidade(BigDecimal totalQuantidade) {
		this.totalQuantidade = totalQuantidade;
	}

}
