package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioSituacaoGarantia implements Dominio {

	VENCIDA("VENCIDA"), EM_VIGOR("EM VIGOR"), RETORNO_DE_ASSISTENCIA("RETORNO DE ASSISTÊNCIA");

	private String descricao;

	private DominioSituacaoGarantia(String descricao) {

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

	public static Iterable<DominioSituacaoGarantia> list() {

		return Arrays.asList(DominioSituacaoGarantia.EM_VIGOR, DominioSituacaoGarantia.VENCIDA,
				DominioSituacaoGarantia.RETORNO_DE_ASSISTENCIA);
	}
}
