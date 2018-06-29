package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioCondicao implements Dominio {

	NOVA("NOVO"), USADA("USADA");

	private String descricao;

	private DominioCondicao(String descricao) {

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

	public static Iterable<DominioCondicao> list() {

		return Arrays.asList(DominioCondicao.NOVA, DominioCondicao.USADA);
	}

}