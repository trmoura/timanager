package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioSituacaoChamado implements Dominio {

	EM_ANDAMENTO("EM ANDAMENTO"), RESOLVIDO("RESOLVIDO"), PENDENTE("PENDENTE"), CANCELADO("Cancelado"), ABERTO(
			"Aberto"), ENVIADO_PARA_GARANTIA("Enviado p/ Garantia");

	private String descricao;

	private DominioSituacaoChamado(String descricao) {

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

	public static Iterable<DominioSituacaoChamado> list() {

		return Arrays.asList(DominioSituacaoChamado.EM_ANDAMENTO, DominioSituacaoChamado.RESOLVIDO,
				DominioSituacaoChamado.PENDENTE);
	}
}
