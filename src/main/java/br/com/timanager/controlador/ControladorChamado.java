package br.com.timanager.controlador;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.AutorizacoesDAO;
import br.com.timanager.DAO.ChamadosDAO;
import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.ParametrosDAO;
import br.com.timanager.DAO.SetoresDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.dominio.DominioSituacaoChamado;
import br.com.timanager.dominio.DominioTemplateEmail;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Chamado;
import br.com.timanager.modelo.ChamadoDetalhe;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Setor;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.seguranca.Seguranca;
import br.com.timanager.servico.ServicoChamado;
import br.com.timanager.servico.ServicoEmail;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorChamado implements Serializable {

	private static final long serialVersionUID = -2916022548784285390L;

	public static final String NAVEGACAO_NOVO_CHAMADO = "/suporte/novochamado?faces-redirect=true";

	@Inject
	private Seguranca sec;

	@Inject
	private ParametrosDAO parametrosDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	private SetoresDAO setoresDAO;

	@Inject
	private ChamadosDAO chamadosDAO;

	@Inject
	private AutorizacoesDAO autorizacoesDAO;

	@Inject
	private ServicoChamado service;

	@Inject
	private ServicoEmail servicoEmail;

	@Inject
	private UsuariosDAO usuariosDAO;

	private List<Usuario> usuarios;

	private List<Usuario> tecnicos;

	private Chamado chamado;

	private ChamadoDetalhe detalhes;

	private Chamado chamadoSelecionado;

	private List<Chamado> listagemChamados;

	private List<Empresa> listagemEmpresas;

	private List<Setor> listagemSetores;

	public ControladorChamado() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...chamdos");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		this.listagemEmpresas = new ArrayList<Empresa>();
		this.listagemEmpresas = empresasDAO.buscarTodas();
		this.listagemSetores = new ArrayList<Setor>();
		this.listagemSetores = setoresDAO.buscarTodos();
		this.usuarios = usuariosDAO.buscarTodos();
		this.tecnicos = usuariosDAO.buscarUsuariosTecnico();
		this.listagemChamados = chamadosDAO.buscarTodos();
	}

	private void limpar() {
		chamado = new Chamado();
		if (this.chamado.getId() == null) {
			this.chamado.setDataAbertura(DataUtil.getDataAtual());
			this.chamado.setSituacaoChamado(DominioSituacaoChamado.ABERTO);
		}
	}

	public String novoChamado() {
		limpar();
		return this.NAVEGACAO_NOVO_CHAMADO;
	}

	public void salvar() {

		this.chamado.setUsuario(sec.getUsuarioLogado().getUsuario());
		chamado = service.salvar(chamado);
		enviarEmail();
		FacesUtil.addInfoMessage("Chamado salvo com sucesso");
		limpar();
	}

	public void salvarEdicao() {
		this.chamado.setUsuario(sec.getUsuarioLogado().getUsuario());
		chamado = service.salvar(chamado);
		FacesUtil.addInfoMessage("Alteração salva com sucesso");
		limpar();
	}

	public void encerrarChamado() {
		this.chamado.setUsuario(sec.getUsuarioLogado().getUsuario());
		this.chamado.setSituacaoChamado(DominioSituacaoChamado.RESOLVIDO);
		this.chamado.setDataEncerramento(DataUtil.getDataAtual());
		chamado = service.salvar(chamado);
		enviarEmailEncerramentoChamado();
		FacesUtil.addInfoMessage("Chamado encerrado com sucesso");
		limpar();
	}

	private ControladorChamado enviarEmail() throws NegocioException {

		try {

			// FIXME: Mudar implementação, só enviará email para usuários
			// flagados no cadastro.
			for (Usuario u : this.usuarios) {
				this.servicoEmail.enviarEmail(u.getEmail(), u.getCargo() + " - " + u.getNome(), "ABERTURA DE CHAMADO",
						DominioTemplateEmail.ABERTURA_CHAMADO, this.getParametrosEmail());
			}

		} catch (final Exception e) {
			throw new NegocioException("Erro no envio de email.");
		}

		return this;
	}

	protected HashMap<String, Object> getParametrosEmail() throws ParseException {

		final HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idChamado", this.chamado.getId());
		parametros.put("descricao", this.chamado.getDescricaoChamado());
		parametros.put("situacao", this.chamado.getSituacaoChamado().getDescricao());
		parametros.put("dataAbertura", DataUtil.getDataComoString(this.chamado.getDataAbertura()));
		parametros.put("empresa", this.chamado.getEmpresa().getNomeFantasia());
		parametros.put("setor", this.chamado.getSetor().getDescricao());
		parametros.put("solicitante", this.getChamado().getUsuario().getNome());
		parametros.put("tecnico", this.getChamado().getTecnico().getNome());

		return parametros;
	}

	private ControladorChamado enviarEmailEncerramentoChamado() throws NegocioException {

		try {

			for (Usuario u : this.usuarios) {
				this.servicoEmail.enviarEmail(u.getEmail(), u.getCargo() + " - " + u.getNome(),
						"ENCERRAMENTO DE CHAMADO", DominioTemplateEmail.ENCERRAMENTO_CHAMADO,
						this.getParametrosEmailEncerramento());
			}

		} catch (final Exception e) {
			throw new NegocioException("Erro no envio de email.");
		}

		return this;
	}

	protected HashMap<String, Object> getParametrosEmailEncerramento() throws ParseException {

		final HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("idChamado", this.chamado.getId());
		parametros.put("descricao", this.chamado.getDescricaoChamado());
		parametros.put("parecer", this.chamado.getParecer());
		parametros.put("situacao", this.chamado.getSituacaoChamado().getDescricao());
		parametros.put("dataAbertura", DataUtil.getDataComoString(this.chamado.getDataAbertura()));
		parametros.put("dataEncerramento", DataUtil.getDataComoString(this.chamado.getDataEncerramento()));
		parametros.put("empresa", this.chamado.getEmpresa().getNomeFantasia());
		parametros.put("setor", this.chamado.getSetor().getDescricao());
		parametros.put("solicitante", this.getChamado().getUsuario().getNome());
		parametros.put("tecnico", this.getChamado().getTecnico().getNome());

		return parametros;
	}

	public void acaoEditarChamadoSelecionado(ActionEvent actionEvent) {

		Long idChamado = (Long) actionEvent.getComponent().getAttributes().get("idChamado");
		this.chamado = chamadosDAO.porId(idChamado);

	}

	public void adicionarinteracao() {

		int linha = this.chamado.getChamadoDetalhes().size() == 0 ? 0 : this.chamado.getChamadoDetalhes().size();
		this.chamado.getChamadoDetalhes().add(linha, this.detalhes);

	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public List<Empresa> getListagemEmpresas() {
		return listagemEmpresas;
	}

	public void setListagemEmpresas(List<Empresa> listagemEmpresas) {
		this.listagemEmpresas = listagemEmpresas;
	}

	public List<Setor> getListagemSetores() {
		return listagemSetores;
	}

	public void setListagemSetores(List<Setor> listagemSetores) {
		this.listagemSetores = listagemSetores;
	}

	public boolean isTemChamadoSalvo() {
		return this.chamado.getId() != null;
	}

	public List<Chamado> getListagemChamados() {
		return listagemChamados;
	}

	public void setListagemChamados(List<Chamado> listagemChamados) {
		this.listagemChamados = listagemChamados;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Usuario> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Usuario> tecnicos) {
		this.tecnicos = tecnicos;
	}

	public Chamado getChamadoSelecionado() {
		return chamadoSelecionado;
	}

	public void setChamadoSelecionado(Chamado chamadoSelecionado) {
		this.chamadoSelecionado = chamadoSelecionado;
	}

	public boolean isEstaEmAndamento() {
		return DominioSituacaoChamado.EM_ANDAMENTO.equals(this.chamado.getSituacaoChamado());
	}

	public boolean isTemInteracoes() {
		return CollectionUtils.isNotEmpty(this.chamado.getChamadoDetalhes());
	}

	public boolean isPossuiAutorizacao() {

		return this.autorizacoesDAO
				.buscarAutorizacoesGrupo(this.sec.getUsuarioLogado().getUsuario().getGrupos().get(0).getId())
				.contains(this.autorizacoesDAO.getROLEManutencaoChamado());
	}

	public boolean isPodeAdiconarInteracao() {
		if (isEstaEmAndamento() && isTemInteracoes()) {
			return false;
		}

		return true;
	}

	public ChamadoDetalhe getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(ChamadoDetalhe detalhes) {
		this.detalhes = detalhes;
	}
}
