package br.com.timanager.controlador;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.EstoquesDAO;
import br.com.timanager.DAO.NotasEntradaDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.dominio.DominioOrigemMovimentacao;
import br.com.timanager.dominio.DominioSituacaoNotaFiscal;
import br.com.timanager.dominio.DominioTipoMovimentacao;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Estoque;
import br.com.timanager.modelo.GarantiaDetalhe;
import br.com.timanager.modelo.ItemNotaEntrada;
import br.com.timanager.modelo.ItemTempXML;
import br.com.timanager.modelo.Movimentacao;
import br.com.timanager.modelo.NotaFiscalEntrada;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.seguranca.Seguranca;
import br.com.timanager.servico.ServicoEstoque;
import br.com.timanager.servico.ServicoMovimentacao;
import br.com.timanager.servico.ServicoNotaEntrada;
import br.com.timanager.util.DataUtil;
import br.com.timanager.util.Utils;

@Named
@ViewScoped
public class ControladorNotaFiscalEntrada implements Serializable {

	private static final long serialVersionUID = -3025689671004593384L;

	public static final String NAVEGACAO_NOVA_GARANTIA = "/controle/garantia/montargarantia?faces-redirect=true";

	private String forcaRefresh;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private UsuariosDAO usuariosDAO;

	@Inject
	private NotasEntradaDAO notassDAO;

	@Inject
	private EstoquesDAO estoquesDAO;

	private Usuario usuario;

	@Inject
	private ServicoNotaEntrada servicoNota;

	@Inject
	private ServicoEstoque servicoEstoque;

	@Inject
	private ServicoMovimentacao servicoMovimentacao;

	@Inject
	private Seguranca seguranca;

	private NotaFiscalEntrada nota;

	private ItemNotaEntrada itemNota;

	private ItemTempXML itemTempXML;

	private Peca peca;

	private List<Peca> listagemPecas;

	private List<ItemTempXML> listaItensTempXLM;

	private List<Peca> listaPecasSelecionadas;

	private List<ItemNotaEntrada> itensLista;

	private List<Empresa> listagemEmpresas;

	private String filtro;

	private boolean importacao;
	private byte[] arquivoXML;

	private UploadedFile arquivo;

	public ControladorNotaFiscalEntrada() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...nota fiscal");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();

		}

	}

	private void preencherListagem() {
		listagemEmpresas = new ArrayList<Empresa>();
		listagemEmpresas = empresasDAO.buscarTodas();
	}

	public void salvar() {
		try {

			this.usuario = seguranca.getUsuarioLogado().getUsuario();
			this.nota.setUsuario(this.usuario);
			this.nota.setSituacao(DominioSituacaoNotaFiscal.LANCADA);
			this.nota = servicoNota.salvar(this.nota);
			this.atualizaEstoques();
			this.geraMovimentacao();
			FacesUtil.addInfoMessage("Nota Fiscal salva com sucesso");
			novaGarantia();
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o Nota Fiscal");
			erro.printStackTrace();
		}
	}

	private ControladorNotaFiscalEntrada geraMovimentacao() {
		for (ItemNotaEntrada itemNota : this.nota.getItensNotaEntrada()) {
			Movimentacao mov = new Movimentacao();
			mov.setDataMovimentao(DataUtil.getDataAtual());
			mov.setEmpresaEntrada(this.nota.getEmpresa());
			mov.setQuantidade(itemNota.getQuantidade());
			mov.setTipoMovimentacao(DominioTipoMovimentacao.ENTRADA);
			mov.setOrigemMovimentacao(DominioOrigemMovimentacao.NOTA_FISCAL);
			mov.setPecaMovimentacao(itemNota.getItemPeca());
			this.servicoMovimentacao.salvar(mov);
		}
		return this;

	}

	private ControladorNotaFiscalEntrada atualizaEstoques() {
		for (ItemNotaEntrada itemNota : this.nota.getItensNotaEntrada()) {

			if (estoquesDAO.porPecaEmpresa(itemNota.getItemPeca(), itemNota.getItemPeca().getEmpresa()) == null) {
				Estoque estoque = new Estoque();
				estoque.setDataEntradaInicial(DataUtil.getDataAtual());
				estoque.setEmpresa(this.nota.getEmpresa());
				estoque.setPeca(itemNota.getItemPeca());
				estoque.setQuantidade(itemNota.getQuantidade());
				this.servicoEstoque.salvar(estoque);
			} else {
				Estoque estoque = estoquesDAO.porPecaEmpresa(itemNota.getItemPeca(),
						itemNota.getItemPeca().getEmpresa());
				estoque.setQuantidade(estoque.getQuantidade().add(itemNota.getQuantidade()));
				this.servicoEstoque.salvar(estoque);
			}

		}

		return this;

	}

	public String novaGarantia() {
		limpar();
		return ControladorNotaFiscalEntrada.NAVEGACAO_NOVA_GARANTIA;

	}

	public void adicionarPeca() {

		if (CollectionUtils.isNotEmpty(this.listaPecasSelecionadas)) {
			for (Peca pecaItem : this.listaPecasSelecionadas) {
				int linha = this.nota.getItensNotaEntrada().size() == 0 ? 0 : this.nota.getItensNotaEntrada().size();

				if (this.isExistePecaDuplicada(pecaItem)) {
					FacesUtil.addErrorMessage("O Item com ID " + pecaItem.getId() + " já foi adicionado !");
					return;
				}

				ItemNotaEntrada itemNota = new ItemNotaEntrada();
				itemNota.setItemPeca(pecaItem);
				itemNota.setNotaFiscalEntrada(this.nota);
				this.nota.getItensNotaEntrada().add(linha, itemNota);

			}
			this.filtro = "";
			this.listaPecasSelecionadas = new ArrayList<>();
		}

	}

	private boolean isExistePecaDuplicada(Peca pecaItem) {

		return CollectionUtils.exists(this.nota.getItensNotaEntrada(), this.predicadoParaRegistroDuplicado(pecaItem));
	}

	private Predicate<ItemNotaEntrada> predicadoParaRegistroDuplicado(final Peca pecaItem) {

		return new Predicate<ItemNotaEntrada>() {

			@Override
			public boolean evaluate(ItemNotaEntrada registroItemDaNota) {

				return registroItemDaNota.getItemPeca().equals(pecaItem);
			}
		};

	}

	public void acaoRemoverItem(int linhaSelecionada) {
		this.nota.getItensNotaEntrada().remove(linhaSelecionada);
		recalculaTotalNota();
	}

	private Predicate<GarantiaDetalhe> itemSelecionadoLista(Peca pecaSelecionada) {
		return new Predicate<GarantiaDetalhe>() {

			@Override
			public boolean evaluate(GarantiaDetalhe detalhe) {

				return detalhe.getPeca().equals(pecaSelecionada);
			}
		};

	}

	public List<Peca> pesquisarPeca() {
		if (this.nota.getEmpresa() != null) {
			return this.listagemPecas = pecasDAO.buscarPorDescricao(this.filtro, this.nota.getEmpresa().getId());
		} else {
			FacesUtil.addErrorMessage("Selecione uma empresa.");
			return null;
		}

	}

	private void notaLimpa() {
		if (nota.getId() == null) {
			nota.setDataCadastro(DataUtil.getDataAtual());
			nota.setSituacao(DominioSituacaoNotaFiscal.PRE_LANCADA);
		}
	}

	private void limpar() {
		peca = new Peca();
		nota = new NotaFiscalEntrada();

		notaLimpa();

		itensLista = new ArrayList<ItemNotaEntrada>();
		usuario = new Usuario();

	}

	public void lerArquivo() throws IOException {
		if (isTemArquivo()) {
			Reader reader = new InputStreamReader(this.arquivo.getInputstream());
			this.listaItensTempXLM = Utils.processaArquivoXMLNOTA(reader);
			reader.close();
		}
	}

	public void handleArquivoXML(final FileUploadEvent event) {
		this.setArquivoXML(event.getFile().getContents());
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Peca> getListagemPecas() {
		return listagemPecas;
	}

	public void setListagemPecas(List<Peca> listagemPecas) {
		this.listagemPecas = listagemPecas;
	}

	public boolean isNaoEstaVaiza() {
		return this.peca.getId() != null || this.peca != null;
	}

	public List<Empresa> getListagemEmpresas() {
		return listagemEmpresas;
	}

	public void setListagemEmpresas(List<Empresa> listagemEmpresas) {
		this.listagemEmpresas = listagemEmpresas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isTemEmpresa() {
		return this.nota.getEmpresa() != null;
	}

	public boolean isTemNotaSalva() {
		return this.nota.getId() != null;
	}

	public boolean isLancada() {
		if (this.nota != null) {
			return DominioSituacaoNotaFiscal.LANCADA.equals(this.nota.getSituacao());
		} else {
			return false;
		}
	}

	public List<Peca> getListaPecasSelecionadas() {
		return listaPecasSelecionadas;
	}

	public void setListaPecasSelecionadas(List<Peca> listaPecasSelecionadas) {
		this.listaPecasSelecionadas = listaPecasSelecionadas;
	}

	public boolean isPossuiMaisDeUmaPecaSelecionada() {
		return CollectionUtils.size(listaPecasSelecionadas) > 1;
	}

	public void getDescricaoPecasSelecionadas() {
		this.filtro = "";
		List<String> descricoes = new ArrayList<String>();
		for (Peca peca : this.listaPecasSelecionadas) {
			descricoes.add(peca.getDescricao());
		}

		this.filtro = StringUtils.join(descricoes, " | ");
	}

	public NotaFiscalEntrada getNota() {
		if (this.nota == null) {
			nota = new NotaFiscalEntrada();
			nota.setDataCadastro(DataUtil.getDataAtual());
			nota.setSituacao(DominioSituacaoNotaFiscal.PRE_LANCADA);
		}
		return nota;
	}

	public void setNota(NotaFiscalEntrada nota) {
		this.nota = nota;
	}

	public ItemNotaEntrada getItemNota() {
		return itemNota;
	}

	public void setItemNota(ItemNotaEntrada itemNota) {
		this.itemNota = itemNota;
	}

	public List<ItemNotaEntrada> getItensLista() {
		return itensLista;
	}

	public void setItensLista(List<ItemNotaEntrada> itensLista) {
		this.itensLista = itensLista;
	}

	public boolean isImportacao() {
		return importacao;
	}

	public void setImportacao(boolean importacao) {
		this.importacao = importacao;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public byte[] getArquivoXML() {
		return arquivoXML;
	}

	public void setArquivoXML(byte[] arquivoXML) {
		this.arquivoXML = arquivoXML;
	}

	public void vaiImportarXML() {
		this.importacao = true;
	}

	public boolean isTemArquivo() {
		return this.arquivo != null;
	}

	public ItemTempXML getItemTempXML() {
		return itemTempXML;
	}

	public void setItemTempXML(ItemTempXML itemTempXML) {
		this.itemTempXML = itemTempXML;
	}

	public List<ItemTempXML> getListaItensTempXLM() {
		return listaItensTempXLM;
	}

	public void setListaItensTempXLM(List<ItemTempXML> listaItensTempXLM) {
		this.listaItensTempXLM = listaItensTempXLM;
	}

	public void recalculaTotalNota() {
		this.nota.calcularTotalNota();
	}

}
