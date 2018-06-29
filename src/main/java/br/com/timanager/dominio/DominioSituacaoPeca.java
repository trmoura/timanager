package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioSituacaoPeca implements Dominio {

	EM_PRODUÇÃO("EM PRODUÇÃO"), EM_ESTOQUE("EM ESTOQUE"), SEM_USO("SEM USO");

	private String descricao;

	private DominioSituacaoPeca(String descricao) {

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

	public static Iterable<DominioSituacaoPeca> list() {

		return Arrays.asList(DominioSituacaoPeca.EM_PRODUÇÃO, DominioSituacaoPeca.EM_ESTOQUE,
				DominioSituacaoPeca.SEM_USO);
	}
}
