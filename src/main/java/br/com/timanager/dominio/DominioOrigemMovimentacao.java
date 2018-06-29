package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioOrigemMovimentacao implements Dominio {

	NOTA_FISCAL("NOTA FISCAL"), SAIDA_PARA_CONSERTO("SAÍDA PARA CONCERTO"), SAIDA_PARA_USO(
			"SAÍDA PARA USO"), TRANSFERENCIA_ENTRE_EMPRESAS("TRANSFERENCIA ENTRE EMPRESAS");

	private String descricao;

	private DominioOrigemMovimentacao(String descricao) {

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

	public static Iterable<DominioOrigemMovimentacao> list() {

		return Arrays.asList(DominioOrigemMovimentacao.NOTA_FISCAL, DominioOrigemMovimentacao.SAIDA_PARA_CONSERTO,
				DominioOrigemMovimentacao.SAIDA_PARA_USO, DominioOrigemMovimentacao.TRANSFERENCIA_ENTRE_EMPRESAS);
	}

}