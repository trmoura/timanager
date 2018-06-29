package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.timanager.DAO.AutorizacoesDAO;
import br.com.timanager.DAO.GruposDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Autorizacao;
import br.com.timanager.modelo.Grupo;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.servico.ServicoGrupo;

@Named
@ViewScoped
public class ControladorCadastroGrupos implements Serializable {

	private static final long serialVersionUID = 6391267974908707030L;

	@Inject
	private UsuariosDAO usuarios;

	@Inject
	private AutorizacoesDAO autsDAO;

	@Inject
	private GruposDAO grupos;

	@Inject
	private ServicoGrupo service;

	private Usuario usuario;
	private List<Usuario> listUsuarios;
	private Grupo grupo;
	private List<Grupo> listGrupos;
	private String filtro;

	private DualListModel<Autorizacao> autorizacoes;

	private List<Autorizacao> sourceAuts;
	private List<Autorizacao> targetAuts;

	public static final String NAVEGACAO_NOVO_GRUPO = "/cadastro/grupos/novogrupo?faces-redirect=true";

	public ControladorCadastroGrupos() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...controlador grupos");
		if (FacesUtil.isNotPostback()) {
			preecherListagens();
		}
	}

	private void limpar() {
		grupo = new Grupo();
		grupo.setDataCadastro(getPegaDataAtual());
		listGrupos = new ArrayList<Grupo>();
		sourceAuts = new ArrayList<Autorizacao>();
		targetAuts = new ArrayList<Autorizacao>();
	}

	public String novoGrupo() {
		limpar();
		return ControladorCadastroGrupos.NAVEGACAO_NOVO_GRUPO;
	}

	public void preecherListagens() {
		listGrupos = grupos.grupos();
		sourceAuts = autsDAO.buscarTodas();
		autorizacoes = new DualListModel<>(sourceAuts, targetAuts);
	}

	public void salvar() {
		this.grupo.getAutorizacoes();
		this.grupo = service.salvar(this.grupo);
		FacesUtil.addInfoMessage("Grupo salvo com sucesso!");
		novoGrupo();
		preecherListagens();
	}

	public void onTransfer(TransferEvent event) {

		List<Autorizacao> lista = new ArrayList<>(this.targetAuts);
		List<Autorizacao> lista2 = new ArrayList<>(this.sourceAuts);

		for (Object item : event.getItems()) {
			for (Autorizacao a : lista) {
				if (a.equals((Autorizacao) item)) {
					this.grupo.getAutorizacoes().remove((Autorizacao) item);

				}
			}

			for (Autorizacao a : lista2) {
				if (a.equals((Autorizacao) item)) {
					this.grupo.getAutorizacoes().add((Autorizacao) item);

				}
			}

		}

	}

	public void acaoEditarGrupoSelecionado(ActionEvent actionEvent) {

		Long idGrupo = (Long) actionEvent.getComponent().getAttributes().get("idGrupo");
		this.grupo = grupos.porId(idGrupo);
		this.targetAuts = this.grupo.getAutorizacoes();
		autorizacoes = null;
		autorizacoes = new DualListModel<>(filtroAuts(sourceAuts, targetAuts), targetAuts);
	}

	public List<Autorizacao> filtroAuts(List<Autorizacao> listaSource, List<Autorizacao> listaTarget) {

		List<Autorizacao> diferenca = new ArrayList<>(listaSource);

		for (Autorizacao autS : listaSource) {
			for (Autorizacao autT : listaTarget) {
				if (autS.getNome().equals(autT.getNome())) {
					diferenca.remove(autS);
				}
			}
		}
		return diferenca;
	}

	public Date getPegaDataAtual() {
		Date data = new Date();
		return data;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Usuario> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List<Usuario> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getListGrupos() {
		return listGrupos;
	}

	public void setListGrupos(List<Grupo> listGrupos) {
		this.listGrupos = listGrupos;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public DualListModel<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(DualListModel<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public List<Autorizacao> getSourceAuts() {
		return sourceAuts;
	}

	public void setSourceAuts(List<Autorizacao> sourceAuts) {
		this.sourceAuts = sourceAuts;
	}

	public List<Autorizacao> getTargetAuts() {
		return targetAuts;
	}

	public void setTargetAuts(List<Autorizacao> targetAuts) {
		this.targetAuts = targetAuts;
	}

}
