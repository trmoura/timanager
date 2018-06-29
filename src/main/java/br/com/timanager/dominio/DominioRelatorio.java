package br.com.timanager.dominio;

import java.util.Arrays;

public enum DominioRelatorio {

	TESTE_RETRATO("relatorio-teste-retrato", "relatorio-teste-retrato"), TESTE_PAISAGEM("relatorio-teste-paisagem",
			"relatorio-teste-paisagem"), RELATORIO_ITENS_EMPRESA_SETOR("relatorio-itens-empresa-setor",
					"relatorio-itens-empresa-setor"), RELATORIO_ITENS_EMPRESA_SINTETICO(
							"relatorio-itens-empresa-sintetico",
							"relatorio-itens-empresa-sintetico"), RELATORIO_GARANTIA_EMPRESA(
									"relatorio-garantia-empresa",
									"relatorio-garantia-empresa"), RELATORIO_MOVIMENTACOES_EMPRESA(
											"relatorio-movimentacoes-empresa", "relatorio-movimentacoes-empresa");

	private String nomeDoJasper;

	private String nomeDoArquivo;

	private DominioRelatorio(final String nomeDoJasper, final String nomeDoArquivo) {

		this.nomeDoJasper = nomeDoJasper;
		this.nomeDoArquivo = nomeDoArquivo;
	}

	public String getNomeDoJasper() {

		return this.nomeDoJasper;
	}

	public String getNomeDoArquivo() {

		return this.nomeDoArquivo;
	}

	public static boolean isTeste(DominioRelatorio relatorioEnum) {

		return Arrays.asList(DominioRelatorio.TESTE_RETRATO, DominioRelatorio.TESTE_PAISAGEM).contains(relatorioEnum);
	}
}
