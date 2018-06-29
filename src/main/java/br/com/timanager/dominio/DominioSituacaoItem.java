package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioSituacaoItem implements Dominio {

	NORMAL("NORMAL"),OBSOLETO("OBSOLETO"), IRREPARAVEL("IRREPARÁVEL");

	private String descricao;

	private DominioSituacaoItem(String descricao) {

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

	public static Iterable<DominioSituacaoItem> list() {

		return Arrays.asList(DominioSituacaoItem.NORMAL, DominioSituacaoItem.OBSOLETO, DominioSituacaoItem.IRREPARAVEL);
	}

}