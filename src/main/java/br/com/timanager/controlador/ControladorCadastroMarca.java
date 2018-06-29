package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.MarcasDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Marca;
import br.com.timanager.servico.ServicoMarca;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorCadastroMarca implements Serializable {

	private static final long serialVersionUID = -729863858984644654L;

	@Inject
	private MarcasDAO marcasDAO;

	@Inject
	private ServicoMarca servicoMarca;

	private Marca marca;
	private List<Marca> registros;
	public static final String NAVEGACAO_NOVA_MARCA = "/cadastro/marca/novamarca?faces-redirect=true";

	public ControladorCadastroMarca() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		registros = new ArrayList<Marca>();
		registros = marcasDAO.buscarTodos();
	}

	private void limpar() {
		marca = new Marca();
		if (marca.getId() == null) {
			marca.setDataCadastro(DataUtil.getDataAtual());
		}
		registros = new ArrayList<Marca>();

	}

	public void salvar() {
		try {
			this.marca = servicoMarca.salvar(this.marca);
			FacesUtil.addInfoMessage("Marca salva com sucesso");
			novoMarca();
			preencherListagem();
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o item");
			erro.printStackTrace();
		}
	}

	public String novoMarca() {
		limpar();
		return ControladorCadastroMarca.NAVEGACAO_NOVA_MARCA;
	}

	public void acaoEditarMarcaSelecionado(ActionEvent actionEvent) {

		Long idMarca = (Long) actionEvent.getComponent().getAttributes().get("idMarca");
		this.marca = marcasDAO.porId(idMarca);
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Marca> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Marca> registros) {
		this.registros = registros;
	}
}
