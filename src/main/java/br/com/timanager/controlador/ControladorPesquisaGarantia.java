package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.modelo.Garantia;


@Named
@ViewScoped
public class ControladorPesquisaGarantia implements Serializable {

	private static final long serialVersionUID = -6651092900122950249L;

	@Inject
	private GarantiasDAO garantiaDAO;

	private Garantia garantiaSelecionada;

	private List<Garantia> listaDeGarantias;

	private String filtro;
	private Date dataInicial;
	private Date dataFinal;

	public ControladorPesquisaGarantia() {
		garantiaSelecionada = new Garantia();
	}

	public void pesquisarGarantia() {
		this.listaDeGarantias = new ArrayList<Garantia>();
		this.listaDeGarantias = garantiaDAO.buscarPorData(dataInicial, dataFinal);
	}

	public Garantia getGarantiaSelecionada() {
		return garantiaSelecionada;
	}

	public void setGarantiaSelecionada(Garantia garantiaSelecionada) {
		this.garantiaSelecionada = garantiaSelecionada;
	}

	public List<Garantia> getListaDeGarantias() {
		return listaDeGarantias;
	}

	public void setListaDeGarantias(List<Garantia> listaDeGarantias) {
		this.listaDeGarantias = listaDeGarantias;
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

}
