package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.TiposItemDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.TipoItem;
import br.com.timanager.servico.ServicoTipoItem;
import br.com.timanager.util.DataUtil;

@Named
@ViewScoped
public class ControladorCadastroTipoItem implements Serializable {

	private static final long serialVersionUID = 2021744875485366680L;

	@Inject
	private TiposItemDAO tiposItemDAO;

	@Inject
	private ServicoTipoItem servicoTipoItem;

	private TipoItem tipoItem;
	private List<TipoItem> registros;
	public static final String NAVEGACAO_TIPO_DE_ITEM = "/cadastro/item/tipodeitem?faces-redirect=true";

	public ControladorCadastroTipoItem() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		registros = new ArrayList<TipoItem>();
		registros = tiposItemDAO.buscarTodos();
	}

	private void limpar() {
		tipoItem = new TipoItem();
		if (tipoItem.getId() == null) {
			tipoItem.setDataCadastro(DataUtil.getDataAtual());
		}
		registros = new ArrayList<TipoItem>();

	}

	public void salvar() {
		try {
			this.tipoItem = servicoTipoItem.salvar(this.tipoItem);
			FacesUtil.addInfoMessage("Tipo de Item salvo com sucesso");
			novoTipoItem();
			preencherListagem();
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o registro");
			erro.printStackTrace();
		}
	}

	public String novoTipoItem() {
		limpar();
		return ControladorCadastroTipoItem.NAVEGACAO_TIPO_DE_ITEM;
	}

	public void acaoEditarItemSelecionado(ActionEvent actionEvent) {

		Long idItem = (Long) actionEvent.getComponent().getAttributes().get("idItem");
		this.tipoItem = tiposItemDAO.porId(idItem);
	}

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	public List<TipoItem> getRegistros() {
		return registros;
	}

	public void setRegistros(List<TipoItem> registros) {
		this.registros = registros;
	}

}
