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
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.MovimentacoesDAO;
import br.com.timanager.DAO.ParametrosDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.VO.MovimentacaoEstoqueVisualizacao;
import br.com.timanager.dominio.DominioRelatorio;
import br.com.timanager.interfaces.IArquivo;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Peca;
import br.com.timanager.relatorio.ArquivoJasper;
import br.com.timanager.relatorio.ParametroJasper;
import br.com.timanager.relatorio.RelatorioJasperException;
import br.com.timanager.seguranca.Seguranca;
import br.com.timanager.util.DataUtil;

@Named
@SessionScoped
public class ControladorRelatorioMovimentacoes implements Serializable {

	private static final long serialVersionUID = -6246979648204343765L;

	@Inject
	private ParametrosDAO parametrosDAO;

	@Inject
	private MovimentacoesDAO movimentacoesDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private Seguranca sec;

	private Peca peca;

	private Peca pecaSelecionada;

	private Empresa empresa;

	private List<Empresa> empresas;

	private List<Peca> listagemPecas;

	private List<Peca> listaPecasSelecionadas;

	private List<MovimentacaoEstoqueVisualizacao> listagem;

	private boolean desabilitar = true;

	private String filtro;

	public void inicializar() {
		System.out.println("inicializando...relatório movimentações");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}
	}

	public void limpar() {
		empresa = new Empresa();
		empresas = new ArrayList<Empresa>();
	}

	private void preencherListagem() {
		empresas = this.empresasDAO.buscarTodas();

	}

	public void carregarDadosListagem() {
		this.listagem = this.movimentacoesDAO.buscarMovimentacaoPor(this.empresa, this.pecaSelecionada);
		if (CollectionUtils.isNotEmpty(listagem)) {
			this.setDesabilitar(false);
		}
	}

	public List<Peca> pesquisarPeca() {
		if (this.getEmpresa() != null) {
			return this.setListagemPecas(pecasDAO.buscarPorDescricao(this.getFiltro(), this.getEmpresa().getId()));
		} else {
			FacesUtil.addErrorMessage("Selecione uma empresa.");
			return null;
		}

	}

	public void getDescricaoPecasSelecionadas() {
		this.filtro = "";
		this.filtro = pecaSelecionada.getDescricao();
	}

	public StreamedContent acaoDownload() {

		IArquivo arquivo;

		final HashMap<String, Object> parametros = new HashMap<String, Object>();

		try {

			parametros.put("EMPRESA", this.getEmpresa());
			parametros.put("LOGO",
					this.getEmpresa().getLogo() != null ? new ByteArrayInputStream(this.getEmpresa().getLogo()) : null);
			parametros.put("USUARIO", this.sec.getUsuarioLogado().getUsuario().getNome());

			// arquivo = new
			// ArquivoJasper(ParametroJasper.criarPDF(DominioRelatorio.RELACAO_ITENS_SETOR,
			// parametros));

			arquivo = new ArquivoJasper(
					ParametroJasper.criarPDF(DominioRelatorio.RELATORIO_MOVIMENTACOES_EMPRESA, parametros,
							this.getListagem()),
					StringUtils.join("relatorio-movimentacoes-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"));

			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo.getConteudo()),
					arquivo.getTipoDeArquivo().getContentType(), StringUtils.join("relatorio-movimentacoes-",
							DataUtil.toString(DataUtil.getDataAtual(), "yyyyMMddHHmmss"), ".pdf"));

		} catch (Exception e) {
			new RelatorioJasperException();
			return null;
		}

	}

	public boolean isPossuiRegistros() {
		return CollectionUtils.isNotEmpty(this.getListagem());
	}

	public void desabilitadoBotao() {
		this.setDesabilitar(true);
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

	public List<MovimentacaoEstoqueVisualizacao> getListagem() {
		return listagem;
	}

	public void setListagem(List<MovimentacaoEstoqueVisualizacao> listagem) {
		this.listagem = listagem;
	}

	public boolean isDesabilitar() {
		return desabilitar;
	}

	public void setDesabilitar(boolean desabilitar) {
		this.desabilitar = desabilitar;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public List<Peca> getListagemPecas() {
		return listagemPecas;
	}

	public List<Peca> setListagemPecas(List<Peca> listagemPecas) {
		this.listagemPecas = listagemPecas;
		return listagemPecas;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Peca> getListaPecasSelecionadas() {
		return listaPecasSelecionadas;
	}

	public void setListaPecasSelecionadas(List<Peca> listaPecasSelecionadas) {
		this.listaPecasSelecionadas = listaPecasSelecionadas;
	}

	public Peca getPecaSelecionada() {
		return pecaSelecionada;
	}

	public void setPecaSelecionada(Peca pecaSelecionada) {
		this.pecaSelecionada = pecaSelecionada;
	}

}
