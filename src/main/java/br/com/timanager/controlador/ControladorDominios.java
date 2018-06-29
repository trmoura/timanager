package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import br.com.timanager.dominio.DominioCargo;
import br.com.timanager.dominio.DominioCondicao;
import br.com.timanager.dominio.DominioMotivoSaidaEntrada;
import br.com.timanager.dominio.DominioOrigemMovimentacao;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoChamado;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.dominio.DominioSituacaoItem;
import br.com.timanager.dominio.DominioSituacaoNotaFiscal;
import br.com.timanager.dominio.DominioSituacaoPeca;
import br.com.timanager.dominio.DominioTipoMovimentacao;
import br.com.timanager.dominio.DominioTipoProduto;

@ApplicationScoped
public class ControladorDominios implements Serializable {

	private static final long serialVersionUID = 8213661415478182924L;

	@Produces
	@ApplicationScoped
	@Named("condicoesPeca")
	public List<DominioCondicao> getCondicoesPeca() {

		return Arrays.asList(DominioCondicao.NOVA, DominioCondicao.USADA);
	}

	@Produces
	@ApplicationScoped
	@Named("situacoesGarantia")
	public List<DominioSituacaoGarantia> getSituacoesGarantia() {

		return Arrays.asList(DominioSituacaoGarantia.EM_VIGOR, DominioSituacaoGarantia.VENCIDA);
	}

	@Produces
	@ApplicationScoped
	@Named("situacoesChamado")
	public List<DominioSituacaoChamado> getSituacoesChamado() {

		return Arrays.asList(DominioSituacaoChamado.EM_ANDAMENTO, DominioSituacaoChamado.RESOLVIDO,
				DominioSituacaoChamado.PENDENTE);
	}

	@Produces
	@ApplicationScoped
	@Named("situacoesNotaFiscal")
	public List<DominioSituacaoNotaFiscal> getSituacoesNotaFiscal() {

		return Arrays.asList(DominioSituacaoNotaFiscal.PRE_LANCADA, DominioSituacaoNotaFiscal.LANCADA);
	}

	@Produces
	@ApplicationScoped
	@Named("situacoesPecas")
	public List<DominioSituacaoPeca> getSituacoesPecas() {

		return Arrays.asList(DominioSituacaoPeca.EM_PRODUÇÃO, DominioSituacaoPeca.EM_ESTOQUE,
				DominioSituacaoPeca.SEM_USO);
	}

	@Produces
	@ApplicationScoped
	@Named("tiposDeProduto")
	public List<DominioTipoProduto> getTiposDeProduto() {

		return Arrays.asList(DominioTipoProduto.SIMPLES, DominioTipoProduto.COMPOSTO);
	}

	@Produces
	@ApplicationScoped
	@Named("tiposMovimentecoes")
	public List<DominioTipoMovimentacao> getTiposMovimentecoes() {

		return Arrays.asList(DominioTipoMovimentacao.ENTRADA, DominioTipoMovimentacao.SAIDA);
	}

	@Produces
	@ApplicationScoped
	@Named("origensMovimentecoes")
	public List<DominioOrigemMovimentacao> getOrigensMovimentecoes() {

		return Arrays.asList(DominioOrigemMovimentacao.NOTA_FISCAL, DominioOrigemMovimentacao.SAIDA_PARA_CONSERTO,
				DominioOrigemMovimentacao.SAIDA_PARA_USO, DominioOrigemMovimentacao.TRANSFERENCIA_ENTRE_EMPRESAS);
	}

	@Produces
	@ApplicationScoped
	@Named("cargos")
	public List<DominioCargo> getCargos() {

		return Arrays.asList(DominioCargo.DIRETOR, DominioCargo.GERENTE, DominioCargo.AUXILIAR, DominioCargo.SUPERVISOR,
				DominioCargo.TECNICO);
	}

	@Produces
	@ApplicationScoped
	@Named("motivosSaida")
	public List<DominioMotivoSaidaEntrada> getMotivosSaida() {

		return Arrays.asList(DominioMotivoSaidaEntrada.DEFEITO_DE_FABRICA, DominioMotivoSaidaEntrada.AVARIA_DE_USO);
	}

	@Produces
	@ApplicationScoped
	@Named("situacoesItem")
	public List<DominioSituacaoItem> getSituacoesItem() {

		return Arrays.asList(DominioSituacaoItem.NORMAL, DominioSituacaoItem.IRREPARAVEL, DominioSituacaoItem.OBSOLETO);
	}

	@Produces
	@ApplicationScoped
	@Named("logicosSimNao")
	public List<DominioSimNao> getLogicosSimNao() {

		return Arrays.asList(DominioSimNao.values());
	}
}