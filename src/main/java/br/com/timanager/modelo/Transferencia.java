package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "transferencia")
public class Transferencia implements BaseEntity, Serializable {

	private static final long serialVersionUID = 8987369132534788683L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_entrada", nullable = true)
	private Empresa empresaEntrada;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_saida", nullable = true)
	private Empresa empresaSaida;

	@Column(name = "observacao", length = 350)
	private String observacao;

	@OneToMany(mappedBy = "transferencia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<TransferenciaDetalhe> transferenciaDetalhes = new ArrayList<TransferenciaDetalhe>();

	@Override
	public Long getId() {

		return this.id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Empresa getEmpresaEntrada() {
		return empresaEntrada;
	}

	public void setEmpresaEntrada(Empresa empresaEntrada) {
		this.empresaEntrada = empresaEntrada;
	}

	public Empresa getEmpresaSaida() {
		return empresaSaida;
	}

	public void setEmpresaSaida(Empresa empresaSaida) {
		this.empresaSaida = empresaSaida;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<TransferenciaDetalhe> getTransferenciaDetalhes() {
		return transferenciaDetalhes;
	}

	public void setTransferenciaDetalhes(List<TransferenciaDetalhe> transferenciaDetalhes) {
		this.transferenciaDetalhes = transferenciaDetalhes;
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
		Transferencia other = (Transferencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
