package br.com.timanager.controlador;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.DefaultRequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.ParametrosDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.DAO.SetoresDAO;
import br.com.timanager.VO.RelatorioSinteticoVisualizacao;
import br.com.timanager.dominio.DominioRelatorio;
import br.com.timanager.interfaces.IArquivo;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Setor;
import br.com.timanager.relatorio.ArquivoJasper;
import br.com.timanager.relatorio.ParametroJasper;
import br.com.timanager.relatorio.RelatorioJasperException;
import br.com.timanager.seguranca.Seguranca;
import br.com.timanager.util.DataUtil;

@Named
@SessionScoped
public class ControladorRelatorioItens implements Serializable {

	private static final long serialVersionUID = -3470006982929758440L;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private SetoresDAO setoresDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private Seguranca sec;

	@Inject
	private ParametrosDAO parametrosDAO;

	private List<Peca> listagem;

	private List<RelatorioSinteticoVisualizacao> listagemSintetico;

	private List<Empresa> empresas;

	private List<Setor> setores;

	private Empresa empresa;

	private boolean desabilitar = true;

	public void inicializar() {
		System.out.println("inicializando...relatório empresas/setor");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}
	}

	public ControladorRelatorioItens() {
		limpar();
	}

	public void limpar() {
		empresa = new Empresa();
		empresas = new ArrayList<Empresa>();
		listagem = new ArrayList<Peca>();
		setores = new ArrayList<Setor>();
	}

	private void preencherListagem() {
		empresas = this.empresasDAO.buscarTodas();

	}

	public void carregarDadosListagem() {
		this.listagem = this.pecasDAO.buscarPecasPorEmpresa(this.empresa);
		if (CollectionUtils.isNotEmpty(listagem)) {
			this.desabilitar = false;
		}
	}

	public void carregarDadosListagemSintetico() {
		// erro no transformer
		this.listagemSintetico = this.pecasDAO.buscarTotalPecas(this.empresa);
		if (CollectionUtils.isNotEmpty(listagemSintetico)) {
			this.desabilitar = false;
		}
	}

	public StreamedContent acaoDownload() {

		IArquivo arquivo;

		final HashMap<String, Object> parametros = new HashMap<String, Object>();

		try {
			parametros.put("EMPRESA", this.getEmpresa().getId());
			parametros.put("LOGO",
					this.getEmpresa().getLogo() != null ? new ByteArrayInputStream(this.getEmpresa().getLogo()) : null);
			parametros.put("USUARIO", this.sec.getUsuarioLogado().getUsuario().getNome());

			// arquivo = new
			// ArquivoJasper(ParametroJasper.criarPDF(DominioRelatorio.RELACAO_ITENS_SETOR,
			// parametros));

			arquivo = new ArquivoJasper(
					ParametroJasper.criarPDF(DominioRelatorio.RELATORIO_ITENS_EMPRESA_SETOR, parametros),
					StringUtils.join("relatorio-itens-setor-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"),
					"BD");

			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getConteudo()),
					arquivo.getTipoDeArquivo().getContentType(), StringUtils.join("relatorio-itens-setor-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"));

		} catch (Exception e) {
			new RelatorioJasperException();
			return null;
		}

	}

	public StreamedContent acaoDownloadSintetico() {

		IArquivo arquivo;

		final HashMap<String, Object> parametros = new HashMap<String, Object>();

		try {
			parametros.put("EMPRESA", this.getEmpresa().getId());
			parametros.put("LOGO",
					this.getEmpresa().getLogo() != null ? new ByteArrayInputStream(this.getEmpresa().getLogo()) : null);
			parametros.put("USUARIO", this.sec.getUsuarioLogado().getUsuario().getNome());

			// arquivo = new
			// ArquivoJasper(ParametroJasper.criarPDF(DominioRelatorio.RELACAO_ITENS_SETOR,
			// parametros));

			arquivo = new ArquivoJasper(
					ParametroJasper.criarPDF(DominioRelatorio.RELATORIO_ITENS_EMPRESA_SINTETICO, parametros),
					StringUtils.join("relatorio-itens-sintetico-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"),
					"BD");

			this.empresa = new Empresa();
			DefaultRequestContext.getCurrentInstance().update("frm:btDown");

			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getConteudo()),
					arquivo.getTipoDeArquivo().getContentType(), StringUtils.join("relatorio-itens-sintetico-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"));

		} catch (Exception e) {
			new RelatorioJasperException();
			return null;
		}

	}

	public boolean isPossuiRegistros() {
		return CollectionUtils.isNotEmpty(this.getListagem());
	}

	/*
	 * public StreamedContent acaoDownload() throws RelatorioJasperException {
	 * 
	 * final HashMap<String, Object> parametros = new HashMap<String, Object>();
	 * parametros.put("PREFEITURA", this.getPrefeitura());
	 * parametros.put("BRASAO", this.getPrefeitura().getBrasao() != null ? new
	 * ByteArrayInputStream(this.getPrefeitura().getBrasao()) : null);
	 * parametros.put("USUARIO", this.getUsuarioLogado().getDescricao()); return
	 * this.geradorRelatorioJasper.gerarRelatorio(ParametroJasper.criarPDF(
	 * DominioRelatorio.RELATORIO_FISCALIZACAO, parametros,
	 * this.getRegistros())); }
	 */

	public List<Peca> getListagem() {
		return listagem;
	}

	public void setListagem(List<Peca> listagem) {
		this.listagem = listagem;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public Peca filtraSetorPor(Setor setor) {
		return (Peca) CollectionUtils.select(this.listagem, getPredicado(setor));

	}

	public Predicate<Peca> getPredicado(Setor registro) {
		return new Predicate<Peca>() {

			@Override
			public boolean evaluate(Peca peca) {

				return peca.getSetor().equals(registro);
			}

		};
	}

	public List<RelatorioSinteticoVisualizacao> getListagemSintetico() {
		return listagemSintetico;
	}

	public void setListagemSintetico(List<RelatorioSinteticoVisualizacao> listagemSintetico) {
		this.listagemSintetico = listagemSintetico;
	}

	public boolean isExisteEmpresaSelecionada() {
		return this.empresa.getId() != null || this.empresa != null;
	}

	public void desabilitadoBotao() {
		this.desabilitar = true;
	}

	public boolean isDesabilitar() {
		return desabilitar;
	}

	public void setDesabilitar(boolean desabilitar) {
		this.desabilitar = desabilitar;
	}

}
