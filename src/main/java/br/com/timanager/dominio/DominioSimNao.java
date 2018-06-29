package br.com.timanager.dominio;

import br.com.timanager.interfaces.Dominio;

public enum DominioSimNao implements Dominio {

	/**
	 * Não.
	 */
	N,
	/**
	 * Sim.
	 */
	S;

	/**
	 * Retorna o numero correspondente a situacao.
	 * 
	 * @return 0 - Nao, 1 - Sim
	 */
	@Override
	public Integer getCodigo() {

		return this.ordinal();
	}

	/**
	 * Retorna a descricao da situacao.
	 * 
	 * @return descricao
	 */
	@Override
	public String getDescricao() {

		switch (this) {
			case S:
				return "SIM";
			case N:
				return "NÃO";

			default:
				return "";
		}
	}

	/**
	 * Método criado para ajudar os mapeamentos sintéticos para boolean.
	 * 
	 * @return true se o dominion for S, false caso contrario
	 */
	public boolean isSim() {

		switch (this) {
			case S:
				return Boolean.TRUE.booleanValue();
			case N:
			default:
				return Boolean.FALSE.booleanValue();
		}
	}

	/**
	 * Retorna um instancia da classe baseada no valor booleano passado, mapeando o valor boolean para o enum.
	 * 
	 * @param valor
	 *            boolean para ser mapeado
	 * @return S para true, N para false
	 */
	public static DominioSimNao getInstance(final boolean valor) {

		if (valor) {
			return DominioSimNao.S;
		} else {
			return DominioSimNao.N;
		}
	}

}