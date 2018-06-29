package br.com.timanager.dominio;

import java.util.Arrays;

import br.com.timanager.interfaces.Dominio;

public enum DominioCargo implements Dominio {

	DIRETOR("DIRETOR"), GERENTE("GERENTE"), SUPERVISOR("SUPERVISOR"), TECNICO("TECNICO"), AUXILIAR("AUXILIAR");

	private String descricao;

	private DominioCargo(String descricao) {

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

	public static Iterable<DominioCargo> list() {

		return Arrays.asList(DominioCargo.DIRETOR, DominioCargo.GERENTE, DominioCargo.TECNICO, DominioCargo.SUPERVISOR,
				DominioCargo.AUXILIAR);
	}

}