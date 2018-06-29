package br.com.timanager.VO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import br.com.timanager.dominio.DominioOrigemMovimentacao;

public class MovimentacaoEstoqueVisualizacao implements Serializable {

	private static final long serialVersionUID = 2136740799357319642L;

	BigInteger empresa;

	BigInteger idPeca;

	String descricao;

	BigInteger sequencia;

	Date dataMov;

	BigDecimal quantidadeEntada = BigDecimal.ZERO;

	BigDecimal quantidadeSaida = BigDecimal.ZERO;

	String entrada;

	String saida;

	private DominioOrigemMovimentacao origem;

	public MovimentacaoEstoqueVisualizacao(BigInteger empresa, BigInteger idPeca, String descricao,
			BigInteger sequencia, Date dataMov, String entrada, BigDecimal quantidadeEntrada, String saida,
			BigDecimal quantidadeSaida, String origem) {

		this.empresa = empresa;
		this.idPeca = idPeca;
		this.descricao = descricao;
		this.sequencia = sequencia;
		this.dataMov = dataMov;
		this.quantidadeEntada = quantidadeEntrada;
		this.quantidadeSaida = quantidadeSaida;
		this.entrada = entrada;
		this.saida = saida;
		this.origem = DominioOrigemMovimentacao.valueOf(origem);

	}

	public BigInteger getIdPeca() {
		return idPeca;
	}

	public void setIdPeca(BigInteger idPeca) {
		this.idPeca = idPeca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigInteger getSequencia() {
		return sequencia;
	}

	public void setSequencia(BigInteger sequencia) {
		this.sequencia = sequencia;
	}

	public Date getDataMov() {
		return dataMov;
	}

	public void setDataMov(Date dataMov) {
		this.dataMov = dataMov;
	}

	public BigInteger getEmpresa() {
		return empresa;
	}

	public void setEmpresa(BigInteger empresa) {
		this.empresa = empresa;
	}

	public BigDecimal getQuantidadeEntada() {
		return quantidadeEntada;
	}

	public void setQuantidadeEntada(BigDecimal quantidadeEntada) {
		this.quantidadeEntada = quantidadeEntada;
	}

	public BigDecimal getQuantidadeSaida() {
		return quantidadeSaida;
	}

	public void setQuantidadeSaida(BigDecimal quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public DominioOrigemMovimentacao getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {

		this.origem = DominioOrigemMovimentacao.valueOf(origem);
	}

}
