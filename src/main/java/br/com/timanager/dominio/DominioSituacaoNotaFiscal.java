package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioSituacaoNotaFiscal implements Dominio {

	PRE_LANCADA("PRE-LANÇADA"), LANCADA("LANÇADA");

	private String descricao;

	private DominioSituacaoNotaFiscal(String descricao) {

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

	public static Iterable<DominioSituacaoNotaFiscal> list() {

		return Arrays.asList(DominioSituacaoNotaFiscal.PRE_LANCADA, DominioSituacaoNotaFiscal.LANCADA);
	}
}
