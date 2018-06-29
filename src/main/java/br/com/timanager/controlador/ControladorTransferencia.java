package br.com.timanager.controlador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.EstoquesDAO;
import br.com.timanager.DAO.MovimentacoesDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.DAO.TransferenciasDAO;
import br.com.timanager.VO.PecasEstoqueVisualizacao;
import br.com.timanager.dominio.DominioOrigemMovimentacao;
import br.com.timanager.dominio.DominioTipoMovimentacao;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Estoque;
import br.com.timanager.modelo.Movimentacao;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Transferencia;
import br.com.timanager.modelo.TransferenciaDetalhe;
import br.com.timanager.servico.ServicoEstoque;
import br.com.timanager.servico.ServicoMovimentacao;
import br.com.timanager.servico.ServicoTransferencia;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorTransferencia implements Serializable {

	private static final long serialVersionUID = -9127078766899534638L;

	public static final String NAVEGACAO_NOVA_TRANSFERENCIA = "/controle/transferencia/transferenciadeitens?faces-redirect=true";

	@Inject
	private TransferenciasDAO transferenciasDAO;

	@Inject
	private ServicoMovimentacao servicoMovimentacao;

	@Inject
	private MovimentacoesDAO movimentacoesDAO;

	@Inject
	private ServicoTransferencia service;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private EstoquesDAO estoquesDAO;

	@Inject
	private ServicoEstoque servicoEstoque;

	private Transferencia transferencia;

	private TransferenciaDetalhe transferenciaDetalhe;

	private List<Peca> listaPecas;

	private List<Peca> listaPecasSelecionadas;

	private List<Empresa> listagemEmpresas;

	private Peca peca;

	private Estoque estoqueEntrada;

	private Estoque estoqueSaida;

	private Movimentacao movimentacaoEntrada;

	private Movimentacao movimentacaoSaida;

	private String filtro;

	private boolean habilita = true;

	private List<TransferenciaDetalhe> listaTransferenciaDetalheCalculada;

	private List<PecasEstoqueVisualizacao> listaPecaEstoque;

	private List<PecasEstoqueVisualizacao> listaPecasEstoqueSelecionadas;

	public ControladorTransferencia() {
		transferencia = new Transferencia();
	}

	public void inicializar() {
		System.out.println("inicializando...Transferencia");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();

		}

	}

	public String novaTransferencia() {
		limpar();
		return ControladorTransferencia.NAVEGACAO_NOVA_TRANSFERENCIA;

	}

	private void preencherListagem() {
		listagemEmpresas = new ArrayList<Empresa>();
		listagemEmpresas = empresasDAO.buscarTodas();
	}

	public void limpar() {
		transferencia = new Transferencia();
		listaPecas = new ArrayList<Peca>();
		listaPecasSelecionadas = new ArrayList<Peca>();
		listaTransferenciaDetalheCalculada = new ArrayList<TransferenciaDetalhe>();
		peca = new Peca();
		isHabilita();

		if (transferencia.getId() == null) {
			transferencia.setDataCadastro(DataUtil.getDataAtual());

		}
	}

	public void salvar() {
		try {
			if (!validacoes()) {
				return;
			}
			this.procedimentosBaixas();
			this.geraMovimentacaoEstoque();
			this.transferencia = this.service.salvar(transferencia);
			FacesUtil.addInfoMessage("Transferência Salva com sucesso.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void procedimentosBaixas() {
		try {
			this.transferencia.setTransferenciaDetalhes(this.listaTransferenciaDetalheCalculada);
			this.baixaEstoque();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void baixaEstoque() {
		for (TransferenciaDetalhe d : this.listaTransferenciaDetalheCalculada) {

			estoqueEntrada = estoquesDAO.porPecaEmpresa(d.getPecaTranferencia(),
					d.getTransferencia().getEmpresaEntrada());
			estoqueSaida = estoquesDAO.porPecaEmpresa(d.getPecaTranferencia(), d.getTransferencia().getEmpresaSaida());

			if (estoqueEntrada != null) {
				estoqueEntrada.setQuantidade(estoqueEntrada.getQuantidade().add(d.getQuantidade()));
				estoqueEntrada = servicoEstoque.salvar(estoqueEntrada);
			} else {
				estoqueEntrada = new Estoque();
				estoqueEntrada.setDataEntradaInicial(DataUtil.getDataAtual());
				estoqueEntrada.setEmpresa(d.getTransferencia().getEmpresaEntrada());
				estoqueEntrada.setPeca(d.getPecaTranferencia());
				estoqueEntrada.setQuantidade(d.getQuantidade());
				estoqueEntrada = servicoEstoque.salvar(estoqueEntrada);
			}

			if (estoqueSaida != null) {
				estoqueSaida.setQuantidade(estoqueSaida.getQuantidade().subtract(d.getQuantidade()));
				estoqueSaida = servicoEstoque.salvar(estoqueSaida);
			} else {
				estoqueSaida = new Estoque();
				estoqueSaida.setDataEntradaInicial(DataUtil.getDataAtual());
				estoqueSaida.setEmpresa(d.getTransferencia().getEmpresaEntrada());
				estoqueSaida.setPeca(d.getPecaTranferencia());
				estoqueSaida.setQuantidade(d.getQuantidade());
				estoqueSaida = servicoEstoque.salvar(estoqueSaida);
			}

		}

	}

	public void geraMovimentacaoEstoque() {
		for (TransferenciaDetalhe d : this.listaTransferenciaDetalheCalculada) {
			movimentacaoEntrada = new Movimentacao();
			movimentacaoEntrada.setDataMovimentao(DataUtil.getDataAtual());
			movimentacaoEntrada.setEmpresaEntrada(d.getTransferencia().getEmpresaEntrada());
			movimentacaoEntrada.setEmpresaSaida(d.getTransferencia().getEmpresaSaida());
			movimentacaoEntrada.setOrigemMovimentacao(DominioOrigemMovimentacao.TRANSFERENCIA_ENTRE_EMPRESAS);
			movimentacaoEntrada.setPecaMovimentacao(d.getPecaTranferencia());
			movimentacaoEntrada.setTipoMovimentacao(DominioTipoMovimentacao.ENTRADA);
			movimentacaoEntrada.setQuantidade(d.getQuantidade());
			movimentacaoEntrada = servicoMovimentacao.salvar(movimentacaoEntrada);

			movimentacaoSaida = new Movimentacao();
			movimentacaoSaida.setDataMovimentao(DataUtil.getDataAtual());
			movimentacaoSaida.setEmpresaEntrada(d.getTransferencia().getEmpresaEntrada());
			movimentacaoSaida.setEmpresaSaida(d.getTransferencia().getEmpresaSaida());
			movimentacaoSaida.setOrigemMovimentacao(DominioOrigemMovimentacao.TRANSFERENCIA_ENTRE_EMPRESAS);
			movimentacaoSaida.setPecaMovimentacao(d.getPecaTranferencia());
			movimentacaoSaida.setTipoMovimentacao(DominioTipoMovimentacao.SAIDA);
			movimentacaoSaida.setQuantidade(d.getQuantidade());
			movimentacaoSaida = servicoMovimentacao.salvar(movimentacaoSaida);

		}
	}

	public boolean validacoes() {
		for (TransferenciaDetalhe d : this.listaTransferenciaDetalheCalculada) {
			if (d.getQuantidade().compareTo(BigDecimal.ZERO) == 0) {
				FacesUtil.addErrorMessage("Transfira ao menos 1 item ou remova-o do Grid.");
				return false;
			}
		}

		if (!isTemItensDaEmpresaDeSaida()) {
			FacesUtil.addErrorMessage("Não existem itens á transferir.");
			return false;
		}
		return true;
	}

	public List<PecasEstoqueVisualizacao> pesquisarPeca() {
		if (this.transferencia.getEmpresaSaida() != null) {
			return this.listaPecaEstoque = pecasDAO.buscarPecaEstoque(this.filtro,
					this.transferencia.getEmpresaSaida().getId());
		} else {
			FacesUtil.addErrorMessage("Selecione uma Empresa de Saída.");
			return null;
		}

	}

	public void acaoRemoverItem(int linhaSelecionada) {
		this.transferencia.getTransferenciaDetalhes().remove(linhaSelecionada);
		this.listaTransferenciaDetalheCalculada.remove(linhaSelecionada);
		if (CollectionUtils.isEmpty(this.listaTransferenciaDetalheCalculada)) {
			habilita = true;
		}
	}

	public void checaSeEmpresasSaoDiferentes() {
		if (this.transferencia != null) {

			if (this.transferencia.getEmpresaSaida() != null
					&& (this.transferencia.getEmpresaSaida().equals(this.transferencia.getEmpresaEntrada()))) {
				FacesUtil.addErrorMessage("A Empresa de Saída não pode ser igual a Empresa de Entrada.");
				this.transferencia.setEmpresaEntrada(null);
				return;

			}

		} else {
			FacesUtil.addErrorMessage("Ocorreu um erro inesperado, atualize seu navegador..");
			return;
		}
	}

	public boolean isTemItensDaEmpresaDeSaida() {
		return CollectionUtils.isNotEmpty(this.transferencia.getTransferenciaDetalhes());
	}

	public void adicionarPeca() {

		if (CollectionUtils.isNotEmpty(this.listaPecasEstoqueSelecionadas)) {
			for (PecasEstoqueVisualizacao pecaItem : this.listaPecasEstoqueSelecionadas) {
				int linha = this.transferencia.getTransferenciaDetalhes().size() == 0 ? 0
						: this.transferencia.getTransferenciaDetalhes().size();

				if (this.isExistePecaDuplicadaVisualizacao(pecaItem)) {
					// criar uma lista de erros pois pode ter mais de um .
					FacesUtil.addErrorMessage("O Item com ID " + pecaItem.getId() + " já foi adicionado !");
					return;
				}

				TransferenciaDetalhe detalhe = new TransferenciaDetalhe();

				Peca peca = pecasDAO.porId(pecaItem.getId().longValue());
				detalhe.setPecaTranferencia(peca);
				detalhe.setQuantidade(BigDecimal.ONE);
				detalhe.setQuantidadeAtualDia(pecaItem.getQuantidade());
				detalhe.setTransferencia(this.transferencia);
				this.transferencia.getTransferenciaDetalhes().add(linha, detalhe);
				this.listaTransferenciaDetalheCalculada.add(detalhe);

			}

			if (CollectionUtils.isNotEmpty(this.listaTransferenciaDetalheCalculada)) {
				habilita = false;
			}
			this.filtro = "";
			this.listaPecasEstoqueSelecionadas = new ArrayList<PecasEstoqueVisualizacao>();
		}

	}

	private boolean isExistePecaDuplicadaVisualizacao(PecasEstoqueVisualizacao pecaItem) {

		return CollectionUtils.exists(this.transferencia.getTransferenciaDetalhes(),
				this.predicadoParaRegistroDuplicado(pecaItem));
	}

	private Predicate<TransferenciaDetalhe> predicadoParaRegistroDuplicado(final PecasEstoqueVisualizacao pecaItem) {

		return new Predicate<TransferenciaDetalhe>() {

			@Override
			public boolean evaluate(TransferenciaDetalhe registroTransferenciaDetalhe) {

				return registroTransferenciaDetalhe.getPecaTranferencia().getId().equals(pecaItem.getId().longValue());
			}
		};

	}

	private boolean isExistePecaDuplicadaNosDetalhes(Peca pecaItem) {

		return CollectionUtils.exists(this.listaTransferenciaDetalheCalculada,
				this.predicadoParaRegistroDuplicado(pecaItem));
	}

	private Predicate<TransferenciaDetalhe> predicadoParaRegistroDuplicado(final Peca pecaItem) {

		return new Predicate<TransferenciaDetalhe>() {

			@Override
			public boolean evaluate(TransferenciaDetalhe registroTransferenciaDetalhe) {

				return registroTransferenciaDetalhe.getPecaTranferencia().getId().equals(pecaItem.getId());
			}
		};

	}

	public void calculaQuantidades(int linha) {

		final TransferenciaDetalhe detalhe = this.transferencia.getTransferenciaDetalhes().get(linha);

		for (int i = 0; i < this.transferencia.getTransferenciaDetalhes().size(); i++) {

			if (!detalhe.isQuantidadeTransferenciaOk()) {
				FacesUtil.addErrorMessage("Operação Não permitida, quantidade atual do Item ID - "
						+ detalhe.getPecaTranferencia().getId() + " é :" + detalhe.getQuantidadeAtualDia());
				detalhe.setQuantidade(BigDecimal.ZERO);
				break;
			} else {

				if (CollectionUtils.isNotEmpty(this.listaTransferenciaDetalheCalculada)) {

					if (this.isExistePecaDuplicadaNosDetalhes(detalhe.getPecaTranferencia())) {
						this.listaTransferenciaDetalheCalculada.remove(detalhe);
						detalhe.setTotalQuantidade(detalhe.getQuantidadeAtualDia().subtract(detalhe.getQuantidade()));
						this.listaTransferenciaDetalheCalculada.add(detalhe);
						break;
					} else {
						detalhe.setTotalQuantidade(detalhe.getQuantidadeAtualDia().subtract(detalhe.getQuantidade()));
						this.listaTransferenciaDetalheCalculada.add(detalhe);
						break;
					}
				} else {

					detalhe.setTotalQuantidade(detalhe.getQuantidadeAtualDia().subtract(detalhe.getQuantidade()));
					this.listaTransferenciaDetalheCalculada.add(detalhe);
					break;
				}
			}
		}
	}

	public void getDescricaoPecasSelecionadas() {
		this.filtro = "";
		List<String> descricoes = new ArrayList<String>();
		for (PecasEstoqueVisualizacao peca : this.listaPecasEstoqueSelecionadas) {
			descricoes.add(peca.getDescricao());
		}

		this.filtro = StringUtils.join(descricoes, " | ");
	}

	public boolean isTemEmpresaDeSaida() {
		return this.transferencia.getEmpresaSaida() == null;
	}

	public boolean isNaoEstaVaiza() {
		return this.getPeca().getId() != null || this.getPeca() != null;
	}

	public boolean isTemTransferenciaSalva() {
		return this.transferencia.getId() != null;
	}

	public Transferencia getTransferencia() {
		if (this.transferencia == null) {
			limpar();
		}
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	public TransferenciaDetalhe getTransferenciaDetalhe() {
		return transferenciaDetalhe;
	}

	public void setTransferenciaDetalhe(TransferenciaDetalhe transferenciaDetalhe) {
		this.transferenciaDetalhe = transferenciaDetalhe;
	}

	public List<Peca> getListaPecasSelecionadas() {
		return listaPecasSelecionadas;
	}

	public void setListaPecasSelecionadas(List<Peca> listaPecasSelecionadas) {
		this.listaPecasSelecionadas = listaPecasSelecionadas;
	}

	public List<Empresa> getListagemEmpresas() {
		return listagemEmpresas;
	}

	public void setListagemEmpresas(List<Empresa> listagemEmpresas) {
		this.listagemEmpresas = listagemEmpresas;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Peca> getListaPecas() {
		return listaPecas;
	}

	public void setListaPecas(List<Peca> listaPecas) {
		this.listaPecas = listaPecas;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public List<PecasEstoqueVisualizacao> getListaPecaEstoque() {
		return listaPecaEstoque;
	}

	public void setListaPecaEstoque(List<PecasEstoqueVisualizacao> listaPecaEstoque) {
		this.listaPecaEstoque = listaPecaEstoque;
	}

	public List<PecasEstoqueVisualizacao> getListaPecasEstoqueSelecionadas() {
		return listaPecasEstoqueSelecionadas;
	}

	public void setListaPecasEstoqueSelecionadas(List<PecasEstoqueVisualizacao> listaPecasEstoqueSelecionadas) {
		this.listaPecasEstoqueSelecionadas = listaPecasEstoqueSelecionadas;
	}

	public List<TransferenciaDetalhe> getListaTransferenciaDetalheCalculada() {
		return listaTransferenciaDetalheCalculada;
	}

	public void setListaTransferenciaDetalheCalculada(List<TransferenciaDetalhe> listaTransferenciaDetalheCalculada) {
		this.listaTransferenciaDetalheCalculada = listaTransferenciaDetalheCalculada;
	}

	public Estoque getEstoqueEntrada() {
		return estoqueEntrada;
	}

	public void setEstoqueEntrada(Estoque estoqueEntrada) {
		this.estoqueEntrada = estoqueEntrada;
	}

	public Estoque getEstoqueSaida() {
		return estoqueSaida;
	}

	public void setEstoqueSaida(Estoque estoqueSaida) {
		this.estoqueSaida = estoqueSaida;
	}

	public boolean isHabilita() {
		return habilita;
	}

	public void setHabilita(boolean habilita) {
		this.habilita = habilita;
	}

	public Movimentacao getMovimentacaoEntrada() {
		return movimentacaoEntrada;
	}

	public void setMovimentacaoEntrada(Movimentacao movimentacaoEntrada) {
		this.movimentacaoEntrada = movimentacaoEntrada;
	}

	public Movimentacao getMovimentacaoSaida() {
		return movimentacaoSaida;
	}

	public void setMovimentacaoSaida(Movimentacao movimentacaoSaida) {
		this.movimentacaoSaida = movimentacaoSaida;
	}

}
