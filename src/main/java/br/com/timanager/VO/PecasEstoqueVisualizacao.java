package br.com.timanager.VO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class PecasEstoqueVisualizacao implements Serializable {

	private static final long serialVersionUID = 3221021123450672288L;

	BigInteger id;

	String descricao;

	String numero;

	String patrimonio;

	BigDecimal quantidade = BigDecimal.ZERO;

	BigInteger idEmpresa;

	String empresa;

	public PecasEstoqueVisualizacao(BigInteger id, String descricao, String numero, String patrimonio,
			BigDecimal quantidade, BigInteger idEmpresa, String empresa) {

		this.id = id;
		this.descricao = descricao;
		this.numero = numero;
		this.patrimonio = patrimonio;
		this.quantidade = quantidade;
		this.idEmpresa = idEmpresa;
		this.empresa = empresa;

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(BigInteger idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
