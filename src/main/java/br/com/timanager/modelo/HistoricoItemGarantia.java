package br.com.timanager.modelo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.timanager.dominio.DominioMotivoSaidaEntrada;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "historico_garantia_detalhe")
public class HistoricoItemGarantia implements BaseEntity, Serializable {

	private static final long serialVersionUID = -2465211350536678702L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataSaida")
	private Date dataSaida;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataRetorno")
	private Date dataRetorno;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataInicial")
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataFinal")
	private Date dataFinal;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private DominioSituacaoGarantia situacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivoSaidaEntrada")
	private DominioMotivoSaidaEntrada motivoSaidaEntrada;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "garantiaDetalhe_id", nullable = true)
	private GarantiaDetalhe garantiaDetalhe;

	@Lob
	@Column(name = "documento")
	@Basic(fetch = FetchType.LAZY)
	private byte[] documento;

	@Column(name = "numeroDocumentoServico", length = 15, nullable = true)
	private String numeroDocumentoServico;

	@Column(name = "observacao", length = 500)
	private String observacao;

	public Long getId() {
		return id;
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

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
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

	public DominioSituacaoGarantia getSituacao() {
		return situacao;
	}

	public void setSituacao(DominioSituacaoGarantia situacao) {
		this.situacao = situacao;
	}

	public DominioMotivoSaidaEntrada getMotivoSaidaEntrada() {
		return motivoSaidaEntrada;
	}

	public void setMotivoSaidaEntrada(DominioMotivoSaidaEntrada motivoSaidaEntrada) {
		this.motivoSaidaEntrada = motivoSaidaEntrada;
	}

	public GarantiaDetalhe getGarantiaDetalhe() {
		return garantiaDetalhe;
	}

	public void setGarantiaDetalhe(GarantiaDetalhe garantiaDetalhe) {
		this.garantiaDetalhe = garantiaDetalhe;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getNumeroDocumentoServico() {
		return numeroDocumentoServico;
	}

	public void setNumeroDocumentoServico(String numeroDocumentoServico) {
		this.numeroDocumentoServico = numeroDocumentoServico;
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
		HistoricoItemGarantia other = (HistoricoItemGarantia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
