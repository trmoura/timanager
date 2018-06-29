package br.com.timanager.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemTempXML implements Serializable {

	private static final long serialVersionUID = 552948811538281974L;

	/*
	 * Número da Nota :12422 Data de emissão v2:2015-02-04 Data de emissão
	 * v3:null Produto :3885 Produto :Switch 8 Portas 10/100 SF 800P Intelbras
	 * Produto :55.00 Produto :4492 Produto :HD Externo 1TB Samsung Produto
	 * :320.00 Produto :4472 Produto :Placa Mae 775 DDR3 G41T-M7 ECS OEM Produto
	 * :440.00 Produto :3327 Produto :Memoria 2GB DDR3 1333 M1 (Placa Mae 775)
	 * Produto :198.00 Produto :2544 Produto :VGA 1GB EN8400GS DDR3 64bits EVGA
	 * Produto :126.00 Chave da
	 * Nota:15150208632253000190550010000124221002090990
	 */

	private int id;
	private String numeroNota;
	private String dataEmissao;
	private String codigoProduto;
	private String descricao;
	private BigDecimal valor = BigDecimal.ZERO;
	private BigDecimal quantidade = BigDecimal.ZERO;
	private String chaveNota;

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getChaveNota() {
		return chaveNota;
	}

	public void setChaveNota(String chaveNota) {
		this.chaveNota = chaveNota;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

}
