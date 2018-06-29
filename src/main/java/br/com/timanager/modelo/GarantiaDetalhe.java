package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.timanager.dominio.DominioSituacaoItem;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "garantia_detalhe")
public class GarantiaDetalhe implements BaseEntity, Serializable {

	private static final long serialVersionUID = -4833139177821244641L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "peca_id", nullable = false)
	private Peca peca;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "garantia_id", nullable = false)
	private Garantia garantia;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacaoItemGarantia")
	private DominioSituacaoItem situacaoItemGarantia;

	@OneToMany(mappedBy = "garantiaDetalhe", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HistoricoItemGarantia> historicosItem;

	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Garantia getGarantia() {
		return garantia;
	}

	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public DominioSituacaoItem getSituacaoItemGarantia() {
		return situacaoItemGarantia;
	}

	public void setSituacaoItemGarantia(DominioSituacaoItem situacaoItemGarantia) {
		this.situacaoItemGarantia = situacaoItemGarantia;
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
		GarantiaDetalhe other = (GarantiaDetalhe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<HistoricoItemGarantia> getHistoricosItem() {
		return historicosItem;
	}

	public void setHistoricosItem(List<HistoricoItemGarantia> historicosItem) {
		this.historicosItem = historicosItem;
	}

}
