package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioTipoMovimentacao implements Dominio {

	ENTRADA("ENTRADA"), SAIDA("SAÍDA");

	private String descricao;

	private DominioTipoMovimentacao(String descricao) {

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

	public static Iterable<DominioTipoMovimentacao> list() {

		return Arrays.asList(DominioTipoMovimentacao.ENTRADA, DominioTipoMovimentacao.SAIDA);
	}

}