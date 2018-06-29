package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "estoque")
public class Estoque implements BaseEntity, Serializable {

	private static final long serialVersionUID = 913958174161752301L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public Long getId() {
		return this.id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrada_inicial")
	private Date dataEntradaInicial;

	@NotNull
	@Column(name = "quantidade", precision = 15, scale = 4)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "peca_id", nullable = false)
	private Peca peca;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Empresa empresa;

	public Date getDataEntradaInicial() {
		return dataEntradaInicial;
	}

	public void setDataEntradaInicial(Date dataEntradaInicial) {
		this.dataEntradaInicial = dataEntradaInicial;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		Estoque other = (Estoque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
