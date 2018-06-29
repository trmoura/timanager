package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.ChamadosDAO;
import br.com.timanager.DAO.UsuariosDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Chamado;
import br.com.timanager.modelo.Usuario;

@Named
@ViewScoped
public class ControladorPesquisaChamado implements Serializable {

	private static final long serialVersionUID = 4710500524324552320L;

	@Inject
	private ChamadosDAO chamadosDAO;

	@Inject
	private UsuariosDAO usuariosDAO;

	private Chamado chamado;

	private List<Usuario> tecnicos;

	public List<Usuario> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Usuario> tecnicos) {
		this.tecnicos = tecnicos;
	}

	private String filtro;

	private Date dataInicial;

	private Date dataFinal;

	private Usuario tecnico;

	private List<Chamado> listagemChamados;

	public void inicializar() {
		System.out.println("inicializando...chamdos");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		this.tecnicos = usuariosDAO.buscarUsuariosTecnico();

	}

	public ControladorPesquisaChamado() {
		this.listagemChamados = new ArrayList<Chamado>();
	}

	public void pesquisarChamados() {
		this.listagemChamados = chamadosDAO.buscarChamadosPor(this.dataInicial, this.dataFinal, this.tecnico);
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public List<Chamado> getListagemChamados() {
		return listagemChamados;
	}

	public void setListagemChamados(List<Chamado> listagemChamados) {
		this.listagemChamados = listagemChamados;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Usuario getTecnico() {
		return tecnico;
	}

	public void setTecnico(Usuario tecnico) {
		this.tecnico = tecnico;
	}

}
