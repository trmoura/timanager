package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.quartz.SchedulerException;

import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.servico.ServicoGarantia;

@Named
@ViewScoped
public class ControladorBaixaGarantia implements Serializable {

	private static final long serialVersionUID = 7459604115900132209L;

	public static final String NAVEGACAO_BAIXA_GARANTIA = "/controle/garantia/baixagarantia?faces-redirect=true";

	@Inject
	private GarantiasDAO garantiasDAO;

	@Inject
	private ServicoGarantia servicoGarantia;

	private Garantia garantia;

	private List<Garantia> listagem;

	public void inicializar() throws SchedulerException {
		System.out.println("inicializando...controlador baixa de garantia");
		if (FacesUtil.isNotPostback()) {
			preencherListagens();

		}

	}

	private void preencherListagens() {
		this.listagem = garantiasDAO.garantiasVencidas();

	}

	public String navegacao() {
		return this.NAVEGACAO_BAIXA_GARANTIA;
	}

	public void BaixarGarantia() {
		if (this.garantia.getId() != null) {
			this.garantia.setBaixada(DominioSimNao.S);
			this.garantia = servicoGarantia.salvar(garantia);
			FacesUtil.addInfoMessage("Garantia Baixada com sucesso.");
			this.preencherListagens();
		}
	}

	private void limpar() {
		listagem = new ArrayList<Garantia>();
		garantia = new Garantia();
	}

	public Garantia getGarantia() {
		return garantia;
	}

	public void setGarantia(Garantia garantia) {
		this.garantia = garantia;
	}

	public List<Garantia> getListagem() {
		return listagem;
	}

	public void setListagem(List<Garantia> listagem) {
		this.listagem = listagem;
	}
}
