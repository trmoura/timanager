package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.SetoresDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Setor;
import br.com.timanager.servico.ServicoSetor;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorCadastroSetor implements Serializable {

	private static final long serialVersionUID = 5734169052194817119L;

	@Inject
	private SetoresDAO setoresDAO;

	@Inject
	private ServicoSetor servicoSetor;

	private Setor setor;
	private List<Setor> registros;
	public static final String NAVEGACAO_NOVO_SETOR = "/cadastro/setor/novosetor?faces-redirect=true";

	public ControladorCadastroSetor() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		registros = new ArrayList<Setor>();
		registros = setoresDAO.buscarTodos();
	}

	private void limpar() {
		setor = new Setor();
		if (setor.getId() == null) {
			setor.setDataCadastro(DataUtil.getDataAtual());
		}
		registros = new ArrayList<Setor>();

	}

	public void salvar() {
		try {
			this.setor = servicoSetor.salvar(this.setor);
			FacesUtil.addInfoMessage("Setor salvo com sucesso");
			novoSetor();
			preencherListagem();
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o setor");
			erro.printStackTrace();
		}
	}

	public String novoSetor() {
		limpar();
		return ControladorCadastroSetor.NAVEGACAO_NOVO_SETOR;
	}

	public void acaoEditarSetorSelecionado(ActionEvent actionEvent) {

		Long idSetor = (Long) actionEvent.getComponent().getAttributes().get("idSetor");
		this.setor = setoresDAO.porId(idSetor);
	}

	public List<Setor> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Setor> registros) {
		this.registros = registros;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
}
