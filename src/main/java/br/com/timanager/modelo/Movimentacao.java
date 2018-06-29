package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.timanager.dominio.DominioOrigemMovimentacao;
import br.com.timanager.dominio.DominioTipoMovimentacao;
import br.com.timanager.interfaces.BaseEntity;

@Entity
@Table(name = "movimentacao_estoque")
public class Movimentacao implements BaseEntity, Serializable {

	private static final long serialVersionUID = -8196442696410223654L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Override
	public Long getId() {
		return this.id;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimentacao")
	private DominioTipoMovimentacao tipoMovimentacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "origem_movimentacao")
	private DominioOrigemMovimentacao origemMovimentacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_movimentacao")
	private Date dataMovimentao;

	@NotNull
	@Column(name = "quantidade", precision = 15, scale = 4)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@NotNull
	@Column(name = "saldo_inical", precision = 15, scale = 4)
	private BigDecimal saldoInicial = BigDecimal.ZERO;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_entrada", nullable = true)
	private Empresa empresaEntrada;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_saida", nullable = true)
	private Empresa empresaSaida;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "peca_movimentacao", nullable = true)
	private Peca pecaMovimentacao;

	public DominioTipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(DominioTipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public DominioOrigemMovimentacao getOrigemMovimentacao() {
		return origemMovimentacao;
	}

	public void setOrigemMovimentacao(DominioOrigemMovimentacao origemMovimentacao) {
		this.origemMovimentacao = origemMovimentacao;
	}

	public Date getDataMovimentao() {
		return dataMovimentao;
	}

	public void setDataMovimentao(Date dataMovimentao) {
		this.dataMovimentao = dataMovimentao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
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

	public Peca getPecaMovimentacao() {
		return pecaMovimentacao;
	}

	public void setPecaMovimentacao(Peca pecaMovimentacao) {
		this.pecaMovimentacao = pecaMovimentacao;
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
		Movimentacao other = (Movimentacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

}
