package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.interfaces.BaseEntity;
import br.com.timanager.util.DataUtil;

@Entity
@Table(name = "garantia")
public class Garantia implements BaseEntity, Serializable {

	private static final long serialVersionUID = 6091260134435446184L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataInicial")
	private Date dataInicial;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataFinal")
	private Date dataFinal;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private DominioSituacaoGarantia situacao;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Empresa empresa;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario usuario;

	@NotNull
	@Column(name = "observacao", length = 300)
	private String observacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "baixada")
	private DominioSimNao baixada;

	@Column
	private Integer prazoEmMeses;

	@OneToMany(mappedBy = "garantia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<GarantiaDetalhe> garantiaDetalhes = new ArrayList<GarantiaDetalhe>();

	@Lob
	@Column(name = "documento")
	@Basic(fetch = FetchType.LAZY)
	private byte[] documento;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public DominioSituacaoGarantia getSituacao() {
		return situacao;
	}

	public void setSituacao(DominioSituacaoGarantia situacao) {
		this.situacao = situacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<GarantiaDetalhe> getGarantiaDetalhes() {
		return garantiaDetalhes;
	}

	public void setGarantiaDetalhes(List<GarantiaDetalhe> garantiaDetalhes) {
		this.garantiaDetalhes = garantiaDetalhes;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		Garantia other = (Garantia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Transient
	public boolean isVencida() {
		return DominioSituacaoGarantia.VENCIDA.equals(this.getSituacao());
	}

	public DominioSimNao getBaixada() {
		return baixada;
	}

	public void setBaixada(DominioSimNao baixada) {
		this.baixada = baixada;
	}

	public Integer getPrazoEmMeses() {
		return prazoEmMeses;
	}

	public void setPrazoEmMeses(Integer prazoEmMeses) {
		this.prazoEmMeses = prazoEmMeses;
	}

	@Transient
	public int getPrazoEmMesesGarantia() {
		return DataUtil.getQuantidadeMesesEntreDatas(this.dataInicial, this.dataFinal);
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

}
