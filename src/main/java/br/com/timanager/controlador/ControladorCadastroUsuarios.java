package br.com.timanager.controlador;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.GruposDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Grupo;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.servico.ServicoUsuario;

@Named
@SessionScoped
public class ControladorCadastroUsuarios implements Serializable {

	private static final long serialVersionUID = 6391267974908707030L;

	@Inject
	private UsuariosDAO usuarios;

	@Inject
	private GruposDAO grupos;

	@Inject
	private ServicoUsuario service;

	private Usuario usuario;
	private List<Usuario> listUsuarios;
	private Grupo grupo;
	private List<Grupo> listGrupos;
	private String filtro;

	public static final String NAVEGACAO_NOVO_USUARIO = "/cadastro/usuarios/novoUsuario?faces-redirect=true";

	public ControladorCadastroUsuarios() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...controlador usuarios");
		if (FacesUtil.isNotPostback()) {
			preecherListagens();
		}
	}

	public void chamarUsuarios() {
		listUsuarios = usuarios.buscarTodos();
	}

	private void limpar() {
		usuario = new Usuario();
		usuario.setDataCadastro(getPegaDataAtual());
		listUsuarios = new ArrayList<Usuario>();
		grupo = new Grupo();
		listGrupos = new ArrayList<Grupo>();
	}

	public String novoUsuario() {
		limpar();
		return ControladorCadastroUsuarios.NAVEGACAO_NOVO_USUARIO;
	}

	public void preecherListagens() {
		listGrupos = grupos.grupos();
		listUsuarios = usuarios.buscarTodos();
	}

	public void salvar() {
		this.usuario.getGrupos().add(this.grupo);
		this.usuario = service.salvar(this.usuario);
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
		novoUsuario();
		preecherListagens();
	}

	public void acaoEditarUsuarioSelecionado(ActionEvent actionEvent) {

		Long idUsuario = (Long) actionEvent.getComponent().getAttributes().get("idUsuario");
		this.usuario = usuarios.buscarGruposUsuarioPor(idUsuario);
		// this.listGrupos = this.usuario.getGrupos();

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

	public StreamedContent getFoto() {
		final byte[] array = this.usuario.getFoto() != null ? this.usuario.getFoto()
				: this.usuarios.buscarFotoPor(this.usuario.getId());
		return this.instanciarStreamedContent(array);
	}	
	

	private StreamedContent instanciarStreamedContent(final byte[] array) {
		return array != null ? new DefaultStreamedContent(new ByteArrayInputStream(array))
				: new DefaultStreamedContent();
	}

	public void handleFileUploadFoto(final FileUploadEvent event) {

		this.usuario.setFoto(event.getFile().getContents());
	}

}
