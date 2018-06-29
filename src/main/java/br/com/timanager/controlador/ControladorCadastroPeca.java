package br.com.timanager.controlador;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.MarcasDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.DAO.SetoresDAO;
import br.com.timanager.DAO.TiposItemDAO;
import br.com.timanager.dominio.DominioSituacaoPeca;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Marca;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Setor;
import br.com.timanager.modelo.TipoItem;
import br.com.timanager.servico.ServicoPeca;
import br.com.timanager.util.DataUtil;
import br.com.timanager.util.Utils;

@Named
@ViewScoped
public class ControladorCadastroPeca implements Serializable {

	private static final long serialVersionUID = 2192432266386020610L;

	public static final String NAVEGACAO_NOVA_PECA = "/cadastro/peca/novapeca?faces-redirect=true";
	private Peca peca;
	private Peca pecaSelecionada;
	private List<Peca> registros;
	private List<Marca> listagemMarcas;
	private List<TipoItem> listagemTipos;
	private List<Empresa> listagemEmpresas;
	private List<Setor> listagemSetores;
	private String globalFilter;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private MarcasDAO marcasDAO;

	@Inject
	private TiposItemDAO tiposDAO;

	@Inject
	private SetoresDAO setoresDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private ServicoPeca servicoPeca;

	public ControladorCadastroPeca() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();

		}

	}

	private void preencherListagem() {
		registros = new ArrayList<Peca>();
		registros = pecasDAO.buscarTodos();

		listagemMarcas = new ArrayList<Marca>();
		listagemMarcas = marcasDAO.buscarTodos();

		listagemEmpresas = new ArrayList<Empresa>();
		listagemEmpresas = empresasDAO.buscarTodas();

		listagemTipos = new ArrayList<TipoItem>();
		listagemTipos = tiposDAO.buscarTodos();

		listagemSetores = new ArrayList<Setor>();
		listagemSetores = setoresDAO.buscarTodos();
	}

	private void limpar() {
		peca = new Peca();
		if (peca.getId() == null) {
			peca.setQrcode(Utils.getStringQrcode());
			peca.setDataCadastro(DataUtil.getDataAtual());
		}
		registros = new ArrayList<Peca>();

	}

	public void salvar() {
		try {
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('pecasTable').clearFilters()");
			if (isDuplicando()) {
				this.pecaSelecionada = this.peca;
				this.pecaSelecionada.setId(null);
				this.pecaSelecionada = servicoPeca.salvar(this.pecaSelecionada);
				FacesUtil.addInfoMessage("Item salvo com sucesso");
				novaPeca();
				preencherListagem();
				this.pecaSelecionada = null;
			} else {

				this.peca = servicoPeca.salvar(this.peca);
				FacesUtil.addInfoMessage("Item salvo com sucesso");
				novaPeca();
				preencherListagem();
			}
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar.");
			erro.printStackTrace();
		}
	}

	public String novaPeca() {
		limpar();
		return ControladorCadastroPeca.NAVEGACAO_NOVA_PECA;
	}

	public void handleDocumentoPeca(final FileUploadEvent event) {

		// if (this.getObjetoFormulario().getInformacaoComplementar() == null) {
		// this.getObjetoFormulario().setInformacaoComplementar(new
		// PrefeituraInformacaoComplementar());
		// }
		this.peca.setDocumento(event.getFile().getContents());
	}

	public StreamedContent getDocumentoPeca() {

		final byte[] array = this.peca.getDocumento();
		if (array == null) {
			return null;
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(array));
		// if (this.getObjetoFormulario().getInformacaoComplementar() == null) {
		// return null;
		// } else {
		// final byte[] array =
		// this.getObjetoFormulario().getInformacaoComplementar().getExemploNFSD();
		// if (array == null) {
		// return null;
		// }
		// return new DefaultStreamedContent(new ByteArrayInputStream(array));
		// }

	}

	private StreamedContent instanciarStreamedContent(final byte[] array) {

		return array != null ? new DefaultStreamedContent(new ByteArrayInputStream(array))
				: new DefaultStreamedContent();
	}

	public StreamedContent acaoDownloadDOC() {

		return new DefaultStreamedContent(new ByteArrayInputStream(this.peca.getDocumento()), "application/pdf",
				"Documento_Peça" + ".pdf");
	}

	public void acaoEditarPecaSelecionada(ActionEvent actionEvent) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('pecasTable').clearFilters()");
		Long idPeca = (Long) actionEvent.getComponent().getAttributes().get("idPeca");
		this.peca = pecasDAO.porId(idPeca);

	}

	public void acaoDuplicarPecaSelecionada() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('pecasTable').clearFilters()");
		this.globalFilter = "";
		this.peca = this.pecaSelecionada;

	}

	public void acaoCalcularDiasFuncionamento() {

		if (this.peca.getId() == null) {
			if (isEstaEmProducao()) {
				this.peca.setDataFuncionamento(DataUtil.getDataAtual());
				this.peca.setDiasFuncionando(1);
			} else {
				this.peca.setDataFuncionamento(null);
				this.peca.setDiasFuncionando(0);

			}
		}

		if (this.peca.getId() != null) {
			if (isEstaEmProducao() && isTemDataFuncionamento()) {
				this.peca.setDiasFuncionando(DataUtil.getQuantidadeDiasEntre(this.peca.getDataFuncionamento(),
						DataUtil.toDateAmericano(DataUtil.getDataAtualStringAmericano())));
			} else if (isEstaEmProducao()) {
				this.peca.setDataFuncionamento(DataUtil.getDataAtual());
				this.peca.setDiasFuncionando(1);
			}
		}

	}

	public void atualizaDiasDeFuncionamentoItem() {
		if (this.peca.getId() != null) {
			if (isEstaEmProducao() && isTemDataFuncionamento()) {
				this.peca.setDiasFuncionando(DataUtil.getQuantidadeDiasEntre(this.peca.getDataFuncionamento(),
						DataUtil.toDateAmericano(DataUtil.getDataAtualStringAmericano())));
				this.peca = this.servicoPeca.salvar(this.peca);
			} else if (isEstaEmProducao()) {
				this.peca.setDataFuncionamento(DataUtil.getDataAtual());
				this.peca.setDiasFuncionando(1);
				this.peca = this.servicoPeca.salvar(this.peca);
			}
		}
	}

	private boolean isTemDataFuncionamento() {
		return this.peca.getDataFuncionamento() != null;
	}

	public boolean isEstaEmProducao() {
		return DominioSituacaoPeca.EM_PRODUÇÃO.equals(this.peca.getSituacao());
	}

	public List<Peca> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Peca> registros) {
		this.registros = registros;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public List<Marca> getListagemMarcas() {
		return listagemMarcas;
	}

	public void setListagemMarcas(List<Marca> listagemMarcas) {
		this.listagemMarcas = listagemMarcas;
	}

	public List<Empresa> getListagemEmpresas() {
		return listagemEmpresas;
	}

	public void setListagemEmpresas(List<Empresa> listagemEmpresas) {
		this.listagemEmpresas = listagemEmpresas;
	}

	public List<TipoItem> getListagemTipos() {
		return listagemTipos;
	}

	public void setListagemTipos(List<TipoItem> listagemTipos) {
		this.listagemTipos = listagemTipos;
	}

	public List<Setor> getListagemSetores() {
		return listagemSetores;
	}

	public void setListagemSetores(List<Setor> listagemSetores) {
		this.listagemSetores = listagemSetores;
	}

	public Peca getPecaSelecionada() {
		return pecaSelecionada;
	}

	public void setPecaSelecionada(Peca pecaSelecionada) {
		this.pecaSelecionada = pecaSelecionada;
	}

	public boolean isDuplicando() {
		return this.pecaSelecionada != null;
	}

	public String getGlobalFilter() {
		return globalFilter;
	}

	public void setGlobalFilter(String globalFilter) {
		this.globalFilter = globalFilter;
	}
}
