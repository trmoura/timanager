package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.timanager.DAO.NotasEntradaDAO;
import br.com.timanager.modelo.NotaFiscalEntrada;


@Named
@ViewScoped
public class ControladorPesquisaNotasFiscal implements Serializable {

	private static final long serialVersionUID = 7302475279851291123L;

	@Inject
	private NotasEntradaDAO notasEntradaDAO;

	private NotaFiscalEntrada notaSelecionada;

	private List<NotaFiscalEntrada> listaDeNotas;

	private String filtro;
	private Date dataInicial;
	private Date dataFinal;

	public ControladorPesquisaNotasFiscal() {
		setNotaSelecionada(new NotaFiscalEntrada());
	}

	public void pesquisarGarantia() {
		this.setListaDeNotas(new ArrayList<NotaFiscalEntrada>());
		this.setListaDeNotas(notasEntradaDAO.buscarPorData(dataInicial, dataFinal));
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

	public List<NotaFiscalEntrada> getListaDeNotas() {
		return listaDeNotas;
	}

	public void setListaDeNotas(List<NotaFiscalEntrada> listaDeNotas) {
		this.listaDeNotas = listaDeNotas;
	}

	public NotaFiscalEntrada getNotaSelecionada() {
		return notaSelecionada;
	}

	public void setNotaSelecionada(NotaFiscalEntrada notaSelecionada) {
		this.notaSelecionada = notaSelecionada;
	}

}
