package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.timanager.dominio.DominioSituacaoNotaFiscal;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "nota_fiscal_entrada")
public class NotaFiscalEntrada implements BaseEntity, Serializable {

	private static final long serialVersionUID = -5353687144340804688L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "numeroNota", length = 9, nullable = false)
	private String numeroNota;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private DominioSituacaoNotaFiscal situacao;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dataEmissao")
	private Date dataEmissao;

	@Temporal(TemporalType.DATE)
	@Column(name = "dataCadastro")
	private Date dataCadastro;

	@Column(name = "chaveNota", length = 44, nullable = true)
	private String chaveNota;

	@Column(name = "valorTotal", precision = 15, scale = 4)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@OneToMany(mappedBy = "notaFiscalEntrada", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ItemNotaEntrada> itensNotaEntrada = new ArrayList<ItemNotaEntrada>();

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Empresa empresa;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario usuario;

	@Override
	public Long getId() {
		return this.id;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public DominioSituacaoNotaFiscal getSituacao() {
		return situacao;
	}

	public void setSituacao(DominioSituacaoNotaFiscal situacao) {
		this.situacao = situacao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getChaveNota() {
		return chaveNota;
	}

	public void setChaveNota(String chaveNota) {
		this.chaveNota = chaveNota;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		NotaFiscalEntrada other = (NotaFiscalEntrada) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<ItemNotaEntrada> getItensNotaEntrada() {
		return itensNotaEntrada;
	}

	public void setItensNotaEntrada(List<ItemNotaEntrada> itensNotaEntrada) {
		this.itensNotaEntrada = itensNotaEntrada;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void calcularTotalNota() {
		BigDecimal total = BigDecimal.ZERO;

		//total = total.add(this.valorTotal);

		for (ItemNotaEntrada itemNota : this.getItensNotaEntrada()) {
			if (itemNota.getItemPeca() != null) {
				total = total.add(itemNota.getValorTotaItem());
			}
		}

		this.setValorTotal(total);
	}

}
