package br.com.timanager.VO;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioSinteticoVisualizacao implements Serializable {

	private static final long serialVersionUID = -2564339799372305848L;

	BigDecimal total;

	BigDecimal total_manu;

	private String tipoItem;

	String empresaItem;

	String descricao;

	public RelatorioSinteticoVisualizacao(BigDecimal total, BigDecimal total_manu, String tipoItem,
			String empresaItem, String descricao) {

		this.total = total;
		this.total_manu = total_manu;
		this.setTipoItem(tipoItem);
		this.empresaItem = empresaItem;
		this.descricao = descricao;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotal_manu() {
		return total_manu;
	}

	public void setTotal_manu(BigDecimal total_manu) {
		this.total_manu = total_manu;
	}

	

	public String getEmpresaItem() {
		return empresaItem;
	}

	public void setEmpresaItem(String empresaItem) {
		this.empresaItem = empresaItem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

}