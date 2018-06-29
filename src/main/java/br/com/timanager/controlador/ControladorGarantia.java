package br.com.timanager.controlador;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.modelo.GarantiaDetalhe;
import br.com.timanager.modelo.HistoricoItemGarantia;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.servico.ServicoGarantia;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorGarantia implements Serializable {

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
	private GarantiasDAO garantiasDAO;

	private Usuario usuario;

	@Inject
	private ServicoGarantia servicoGarantia;

	private Garantia garantia;

	private GarantiaDetalhe garantiaDetalhe;

	private Peca peca;

	private HistoricoItemGarantia historicoItemGarantia;

	private List<HistoricoItemGarantia> listaHistoricoItemGarantia;

	private List<HistoricoItemGarantia> listaVisualizacaoHistorico;

	private List<Peca> listagemPecas;
	private List<Peca> listaPecasSelecionadas;

	private List<GarantiaDetalhe> detalhes;

	private List<Empresa> listagemEmpresas;

	private String filtro;

	public ControladorGarantia() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...garantia");
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

			if (!this.validarSalvar()) {
				return;
			}
			// Futuramente pegara o usuario logado
			this.usuario = (usuariosDAO.porId(1L));
			this.garantia.setUsuario(this.usuario);
			this.garantia = servicoGarantia.salvar(this.garantia);
			FacesUtil.addInfoMessage("Garantia salva com sucesso");

		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o Garantia");
			erro.printStackTrace();
		}
	}

	private boolean validarSalvar() throws NegocioException {

		if (this.garantia.getDataFinal().before(this.garantia.getDataInicial())) {
			FacesUtil.addErrorMessage("Data Final não pode ser menor que Data Inicial.");
			return false;
		}
		return true;

	}

	public String novaGarantia() {
		limpar();
		return ControladorGarantia.NAVEGACAO_NOVA_GARANTIA;

	}

	public void adicionarPeca() {
		if (this.garantia.isVencida()) {
			FacesUtil.addErrorMessage("Impossivel Adicionar Item á Garntia Vencida.");
			return;
		}
		if (this.isExistePecaDuplicada()) {
			FacesUtil.addErrorMessage("Esse Item já foi adicionado !");
			return;
		}

		// peça individuais
		if (this.peca.getId() != null) {

			int linha = this.garantia.getGarantiaDetalhes().size() == 0 ? 0
					: this.garantia.getGarantiaDetalhes().size();
			Peca peca = new Peca();
			peca = this.peca;

			GarantiaDetalhe garantiaDetalhe = new GarantiaDetalhe();
			garantiaDetalhe.setPeca(peca);
			garantiaDetalhe.setGarantia(this.garantia);
			this.garantia.getGarantiaDetalhes().add(linha, garantiaDetalhe);

			this.filtro = "";
			this.peca = new Peca();
		}

		// lista de peças

		if (CollectionUtils.isNotEmpty(this.listaPecasSelecionadas)) {
			for (Peca peca : this.listaPecasSelecionadas) {
				int linha = this.garantia.getGarantiaDetalhes().size() == 0 ? 0
						: this.garantia.getGarantiaDetalhes().size();

				GarantiaDetalhe garantiaDetalhe = new GarantiaDetalhe();
				garantiaDetalhe.setPeca(peca);
				garantiaDetalhe.setGarantia(this.garantia);
				this.garantia.getGarantiaDetalhes().add(linha, garantiaDetalhe);

			}
			this.filtro = "";
		}

	}

	public void pegarIdDetalheItem(ActionEvent e) {

		GarantiaDetalhe det = (GarantiaDetalhe) e.getComponent().getAttributes().get("det");
		this.listaHistoricoItemGarantia = det.getId() != null ? garantiasDAO.buscarHistoricosPor(det) : null;
		this.garantiaDetalhe = new GarantiaDetalhe();
		this.garantiaDetalhe = det;

	}

	public void visualizarDetalheItem(ActionEvent e) {

		GarantiaDetalhe det = (GarantiaDetalhe) e.getComponent().getAttributes().get("det");
		this.listaVisualizacaoHistorico = det.getId() != null ? garantiasDAO.buscarHistoricosPor(det) : null;

	}

	public void adcionarHistoricoItem() {
		int linha;
		if (this.listaHistoricoItemGarantia != null) {
			linha = this.listaHistoricoItemGarantia.size() == 0 ? 0 : this.listaHistoricoItemGarantia.size();
		} else {
			listaHistoricoItemGarantia = new ArrayList<HistoricoItemGarantia>();
			if (this.historicoItemGarantia == null) {
				this.historicoItemGarantia = new HistoricoItemGarantia();

			}
			this.historicoItemGarantia.setDataCadastro(DataUtil.getDataAtual());
			linha = listaHistoricoItemGarantia.size();
		}
		this.historicoItemGarantia.setGarantiaDetalhe(garantiaDetalhe);

		listaHistoricoItemGarantia.add(linha, this.historicoItemGarantia);
		this.garantiaDetalhe.setHistoricosItem(listaHistoricoItemGarantia);

	}

	public void guardarHIstorico() {
		this.garantiaDetalhe = servicoGarantia.salvar(garantiaDetalhe);

	}

	private boolean isExistePecaDuplicada() {

		return CollectionUtils.exists(this.garantia.getGarantiaDetalhes(),
				this.predicadoParaRegistroDuplicado(this.peca));
	}

	private Predicate<GarantiaDetalhe> predicadoParaRegistroDuplicado(final Peca peca) {

		return new Predicate<GarantiaDetalhe>() {

			@Override
			public boolean evaluate(GarantiaDetalhe relacionamento) {

				return relacionamento.getPeca().equals(peca);
			}
		};

	}

	public void acaoRemoverItem(int linhaSelecionada) {
		this.garantia.getGarantiaDetalhes().remove(linhaSelecionada);
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
		if (this.garantia.getEmpresa() != null) {
			return this.listagemPecas = pecasDAO.buscarPorDescricao(this.filtro, this.garantia.getEmpresa().getId());
		} else {
			FacesUtil.addErrorMessage("Selecione uma empresa.");
			return null;
		}

	}

	public Garantia getGarantia() {
		if (this.garantia == null) {
			limpar();
		}
		return garantia;
	}

	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	// private void preencherListagem() {
	// registros = new ArrayList<Peca>();
	// registros = pecasDAO.buscarTodos();
	//
	// listagemMarcas = new ArrayList<Marca>();
	// listagemMarcas = marcasDAO.buscarTodos();
	//
	// listagemEmpresas = new ArrayList<Empresa>();
	// listagemEmpresas = empresasDAO.buscarTodas();
	// }
	//
	private void limpar() {
		peca = new Peca();
		garantia = new Garantia();
		if (garantia.getId() == null) {
			garantia.setSituacao(DominioSituacaoGarantia.EM_VIGOR);
		}
		detalhes = new ArrayList<GarantiaDetalhe>();
		historicoItemGarantia = new HistoricoItemGarantia();
		listaHistoricoItemGarantia = new ArrayList<HistoricoItemGarantia>();
		usuario = new Usuario();
		listaPecasSelecionadas = new ArrayList<Peca>();

		if (garantia.getId() == null) {
			garantia.setDataCadastro(DataUtil.getDataAtual());
			historicoItemGarantia.setDataCadastro(DataUtil.getDataAtual());

		}

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

	public List<GarantiaDetalhe> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<GarantiaDetalhe> detalhes) {
		this.detalhes = detalhes;
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

	public HistoricoItemGarantia getHistoricoItemGarantia() {
		return historicoItemGarantia;
	}

	public void setHistoricoItemGarantia(HistoricoItemGarantia historicoItemGarantia) {
		this.historicoItemGarantia = historicoItemGarantia;
	}

	public List<HistoricoItemGarantia> getListaHistoricoItemGarantia() {
		return listaHistoricoItemGarantia;
	}

	public void setListaHistoricoItemGarantia(List<HistoricoItemGarantia> listaHistoricoItemGarantia) {
		this.listaHistoricoItemGarantia = listaHistoricoItemGarantia;
	}

	public void handleDocumentoHistorico(final FileUploadEvent event) {
		this.historicoItemGarantia.setDocumento(event.getFile().getContents());
	}

	public StreamedContent getDocumentoHistorico() {

		final byte[] array = this.historicoItemGarantia.getDocumento();
		if (array == null) {
			return null;
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(array));

	}

	private StreamedContent instanciarStreamedContent(final byte[] array) {

		return array != null ? new DefaultStreamedContent(new ByteArrayInputStream(array))
				: new DefaultStreamedContent();
	}

	public StreamedContent acaoDownloadDOC() {

		return new DefaultStreamedContent(new ByteArrayInputStream(this.historicoItemGarantia.getDocumento()),
				"application/pdf", "Documento_Peça" + ".pdf");
	}
	public StreamedContent acaoDownloadDOCGarantia() {
		
		return new DefaultStreamedContent(new ByteArrayInputStream(this.garantia.getDocumento()),
				"application/pdf", "Documento_garantia" + ".pdf");
	}


	public boolean isTemEmpresa() {
		return this.garantia.getEmpresa() != null;
	}

	public boolean isTemGarantiaSalva() {
		return this.garantia.getId() != null;
	}

	public GarantiaDetalhe getGarantiaDetalhe() {
		return garantiaDetalhe;
	}

	public void setGarantiaDetalhe(GarantiaDetalhe garantiaDetalhe) {
		this.garantiaDetalhe = garantiaDetalhe;
	}

	public List<HistoricoItemGarantia> getListaVisualizacaoHistorico() {
		return listaVisualizacaoHistorico;
	}

	public void setListaVisualizacaoHistorico(List<HistoricoItemGarantia> listaVisualizacaoHistorico) {
		this.listaVisualizacaoHistorico = listaVisualizacaoHistorico;
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

	// public String getSituacoesCondicao() {
	//
	// List<String> situacoes = new ArrayList<String>();
	// for (DominioSituacaoCadastroPessoa situacao : this.getSituacoes()) {
	// situacoes.add("'" + situacao.name() + "'");
	// }
	// return StringUtils.join(situacoes.toArray(), ",");
	// }

	public void handleDocumentoGarantia(final FileUploadEvent event) {
		this.garantia.setDocumento(event.getFile().getContents());
	}

	public StreamedContent getDocumento() {

		final byte[] array = this.garantia.getDocumento();
		if (array == null) {
			return null;
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(array));

	}
}
