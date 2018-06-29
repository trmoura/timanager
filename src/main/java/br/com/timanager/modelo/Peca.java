package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.timanager.dominio.DominioCondicao;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoPeca;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "peca")
public class Peca implements BaseEntity, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7115382429358237730L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "descricao", length = 100)
	private String descricao;

	@Lob
	@Column(name = "documento")
	@Basic(fetch = FetchType.LAZY)
	private byte[] documento;

	@Column(name = "numeroPatrimonio", length = 60, nullable = true)
	private String numeroPatrimonio;

	@NotBlank
	@Column(name = "numeroSerie", length = 60)
	private String numeroSerie;

	@NotBlank
	@Column(name = "modelo", length = 40, nullable = true)
	private String modelo;

	@NotBlank
	@Column(name = "numero_documento", length = 15, nullable = true)
	private String numeroDocumento;

	@NotNull
	@Column(name = "valor", precision = 15, scale = 4)
	private BigDecimal valor = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(name = "condicao")
	private DominioCondicao condicao;

	@Enumerated(EnumType.STRING)
	@Column(name = "situaca")
	private DominioSituacaoPeca situacao;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataFuncionamento", nullable = true)
	private Date dataFuncionamento;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataDocumento")
	private Date dataDocumento;

	@NotNull
	@Column(name = "observacao", length = 300)
	private String observacao;

	@NotBlank
	@Column(name = "qrcode", length = 300)
	private String qrcode;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Marca marca;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private TipoItem tipo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Setor setor;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Empresa empresa;

	@Enumerated(EnumType.STRING)
	@Column(name = "em_manutencao")
	private DominioSimNao emManutencao;

	@Column(name = "diasFuncionando", nullable = true)
	private Integer diasFuncionando = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getNumeroPatrimonio() {
		return numeroPatrimonio;
	}

	public void setNumeroPatrimonio(String numeroPatrimonio) {
		this.numeroPatrimonio = numeroPatrimonio;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DominioCondicao getCondicao() {
		return condicao;
	}

	public void setCondicao(DominioCondicao condicao) {
		this.condicao = condicao;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataFuncionamento() {
		return dataFuncionamento;
	}

	public void setDataFuncionamento(Date dataFuncionamento) {
		this.dataFuncionamento = dataFuncionamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getQrcode() {
		return this.qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoItem getTipo() {
		return tipo;
	}

	public void setTipo(TipoItem tipo) {
		this.tipo = tipo;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
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
		Peca other = (Peca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public DominioSimNao getEmManutencao() {
		return emManutencao;
	}

	public void setEmManutencao(DominioSimNao emManutencao) {
		this.emManutencao = emManutencao;
	}

	public DominioSituacaoPeca getSituacao() {
		return situacao;
	}

	public void setSituacao(DominioSituacaoPeca situacao) {
		this.situacao = situacao;
	}

	public Integer getDiasFuncionando() {
		return diasFuncionando;
	}

	public void setDiasFuncionando(Integer diasFuncionando) {
		this.diasFuncionando = diasFuncionando;
	}

}
