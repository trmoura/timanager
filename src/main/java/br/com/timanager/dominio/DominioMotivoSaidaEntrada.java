package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioMotivoSaidaEntrada implements Dominio {

	DEFEITO_DE_FABRICA("DEFEITO DE FÁBRICA"), AVARIA_DE_USO("AVARIA DE USO");

	private String descricao;

	private DominioMotivoSaidaEntrada(String descricao) {

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

	public static Iterable<DominioMotivoSaidaEntrada> list() {

		return Arrays.asList(DominioMotivoSaidaEntrada.DEFEITO_DE_FABRICA, DominioMotivoSaidaEntrada.AVARIA_DE_USO);
	}

}