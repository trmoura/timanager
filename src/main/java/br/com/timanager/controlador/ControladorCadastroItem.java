package br.com.timanager.controlador;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.ItensDAO;
import br.com.timanager.DAO.MarcasDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Item;
import br.com.timanager.modelo.Marca;
import br.com.timanager.servico.ServicoItem;
import br.com.timanager.util.DataUtil;
import br.com.timanager.util.Utils;



@Named
@ViewScoped
public class ControladorCadastroItem implements Serializable {


	private static final long serialVersionUID = -2485277460845381513L;
	
	public static final String NAVEGACAO_NOVO_ITEM = "/cadastro/item/novoitem?faces-redirect=true";
	private Item item;
	private List<Item> registros;
	private List<Marca> listagemMarcas;
	private List<Empresa> listagemEmpresas;
	

	@Inject
	private ItensDAO itensDAO;
	
	@Inject
	private MarcasDAO marcasDAO;

	@Inject
	private EmpresasDAO empresasDAO;
	
	@Inject
	private ServicoItem servicoItem;

	public ControladorCadastroItem() {
		limpar();
	}

	public void inicializar() {
		System.out.println("inicializando...");
		if (FacesUtil.isNotPostback()) {
			preencherListagem();
		}

	}

	private void preencherListagem() {
		registros = new ArrayList<Item>();
		registros = itensDAO.buscarTodos();
		
		listagemMarcas = new ArrayList<Marca>();
		listagemMarcas = marcasDAO.buscarTodos();

		listagemEmpresas = new ArrayList<Empresa>();
		listagemEmpresas = empresasDAO.buscarTodas();
	}

	private void limpar() {
		item = new Item();
		if (item.getId() == null) {
			item.setQrcode(Utils.getStringQrcode());
			item.setDataCadastro(DataUtil.getDataAtual());
		}
		registros = new ArrayList<Item>();

	}

	public void salvar() {
		try {
			this.item = servicoItem.salvar(this.item);
			FacesUtil.addInfoMessage("Item salvo com sucesso");
			novoItem();
			preencherListagem();
		} catch (RuntimeException erro) {
			FacesUtil.addErrorMessage("Ocorreu um erro ao tentar salvar o item");
			erro.printStackTrace();
		}
	}

	public String novoItem() {
		limpar();
		return ControladorCadastroItem.NAVEGACAO_NOVO_ITEM;
	}

	public void handleDocumentoItem(final FileUploadEvent event) {

		// if (this.getObjetoFormulario().getInformacaoComplementar() == null) {
		// this.getObjetoFormulario().setInformacaoComplementar(new
		// PrefeituraInformacaoComplementar());
		// }
		this.item.setDocumento(event.getFile().getContents());
	}

	public StreamedContent getDocumentoItem() {

		final byte[] array = this.item.getDocumento();
		if (array == null) {
			return null;
		}
		return new DefaultStreamedContent(new ByteArrayInputStream(array));
		// if (this.getObjetoFormulario().getInformacaoComplementar() == null) {
		// return null;
		// } else {
		// final byte[] array =
		// this.getObjetoFormulario().getInformacaoComplementar().getExemploNFSD();
		// if (array == null) {
		// return null;
		// }
		// return new DefaultStreamedContent(new ByteArrayInputStream(array));
		// }

	}

	private StreamedContent instanciarStreamedContent(final byte[] array) {

		return array != null ? new DefaultStreamedContent(new ByteArrayInputStream(array))
				: new DefaultStreamedContent();
	}

	public StreamedContent acaoDownloadDOC() {

		return new DefaultStreamedContent(new ByteArrayInputStream(this.item.getDocumento()), "application/pdf",
				"Documento_Item" + ".pdf");
	}

	public void acaoEditarItemSelecionado(ActionEvent actionEvent) {

		Long idItem = (Long) actionEvent.getComponent().getAttributes().get("idItem");
		this.item = itensDAO.porId(idItem);
		

	}

	public List<Item> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Item> registros) {
		this.registros = registros;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Marca> getListagemMarcas() {
		return listagemMarcas;
	}

	public void setListagemMarcas(List<Marca> listagemMarcas) {
		this.listagemMarcas = listagemMarcas;
	}

	public List<Empresa> getListagemEmpresas() {
		return listagemEmpresas;
	}

	public void setListagemEmpresas(List<Empresa> listagemEmpresas) {
		this.listagemEmpresas = listagemEmpresas;
	}


}
