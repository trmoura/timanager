package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioTipoProduto implements Dominio {

	SIMPLES("SIMPLES"), COMPOSTO("COMPOSTO");

	private String descricao;

	private DominioTipoProduto(String descricao) {

		this.descricao = descricao;

	}

	@Override
	public Integer getCodigo() {

		return this.ordinal();
	}

	@Override
	public String getDescricao() {

		return this.descricao;
	}

	@Override
	public String toString() {

		return this.descricao;
	}

	public static Iterable<DominioTipoProduto> list() {

		return Arrays.asList(DominioTipoProduto.SIMPLES, DominioTipoProduto.COMPOSTO);
	}

}