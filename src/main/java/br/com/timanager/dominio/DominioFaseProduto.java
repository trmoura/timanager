package br.com.timanager.dominio;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

import br.com.timanager.interfaces.Dominio;

public enum DominioFaseProduto implements Dominio {

	TIPO_PRODUTO("TIPO_PRODUTO") {

		@Override
		public Collection<DominioFaseProduto> getFasesAnteriores() {

			return CollectionUtils.emptyCollection();
		}
	},
	SELECAO_ITENS("SELECAO_ITENS") {

		@Override
		public Collection<DominioFaseProduto> getFasesAnteriores() {

			return Arrays.asList(TIPO_PRODUTO, INICIO);
		}
	},

	CONFIMACAO("CONFIMACAO") {

		@Override
		public Collection<DominioFaseProduto> getFasesAnteriores() {

			return Arrays.asList(TIPO_PRODUTO, SELECAO_ITENS, INICIO);
		}
	},
	INICIO("INICIO") {

		@Override
		public Collection<DominioFaseProduto> getFasesAnteriores() {

			return CollectionUtils.emptyCollection();
		}
	};

	private String descricao;

	private DominioFaseProduto(String descricao) {

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

	public abstract Collection<DominioFaseProduto> getFasesAnteriores();
}
