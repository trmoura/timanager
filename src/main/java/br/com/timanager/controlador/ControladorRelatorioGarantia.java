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
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.dominio.DominioRelatorio;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.relatorio.GeradorRelatorioJasper;
import br.com.timanager.relatorio.ParametroJasper;
import br.com.timanager.seguranca.Seguranca;

@Named
@SessionScoped
public class ControladorRelatorioGarantia implements Serializable {

	private static final long serialVersionUID = -6472637395375580124L;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private GarantiasDAO garantiasDAO;

	@Inject
	private Seguranca sec;

	@Inject
	GeradorRelatorioJasper geradorRelatorioJasper;

	private List<Empresa> empresas;

	private Empresa empresa;

	private List<Garantia> listagem;

	private DominioSituacaoGarantia situacao;

	private DominioSimNao baixada;

	private boolean desabilitar = true;

	public void inicializar() {
		System.out.println("inicializando...relatório garantias");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}
	}

	public ControladorRelatorioGarantia() {
		limpar();
	}

	public void limpar() {
		this.empresa = new Empresa();
		this.empresas = new ArrayList<Empresa>();
		this.listagem = new ArrayList<Garantia>();

	}

	private void preencherListagem() {
		this.empresas = empresasDAO.buscarTodas();

	}

	public void carregarDados() {
		this.listagem = garantiasDAO.buscarGarantiasPor(this.empresa, this.situacao, this.baixada);
		if (CollectionUtils.isNotEmpty(listagem)) {
			this.desabilitar = false;
		}else{
			this.desabilitar = true;
			FacesUtil.addInfoMessage("Não existem dados para esses filtros.");
		}
	}

	public void desabilitadoBotao() {
		this.setDesabilitar(true);
	}

	public StreamedContent acaoDownload() {

		final HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("LOGO",
				this.getEmpresa().getLogo() != null ? new ByteArrayInputStream(this.getEmpresa().getLogo()) : null);
		parametros.put("USUARIO", this.sec.getUsuarioLogado().getUsuario().getNome());
		return this.geradorRelatorioJasper.gerarRelatorio(
				ParametroJasper.criarPDF(DominioRelatorio.RELATORIO_GARANTIA_EMPRESA, parametros, this.listagem));
	}

	public List<Garantia> getListagem() {
		return listagem;
	}

	public void setListagem(List<Garantia> listagem) {
		this.listagem = listagem;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public DominioSituacaoGarantia getSituacao() {
		return situacao;
	}

	public void setSituacao(DominioSituacaoGarantia situacao) {
		this.situacao = situacao;
	}

	public boolean isDesabilitar() {
		return desabilitar;
	}

	public void setDesabilitar(boolean desabilitar) {
		this.desabilitar = desabilitar;
	}

	public DominioSimNao getBaixada() {
		return baixada;
	}

	public void setBaixada(DominioSimNao baixada) {
		this.baixada = baixada;
	}

}
