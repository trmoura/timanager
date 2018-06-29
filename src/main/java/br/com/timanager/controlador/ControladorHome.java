package br.com.timanager.controlador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.modelo.Notificacao;
import br.com.timanager.modelo.Peca;
import br.com.timanager.seguranca.Seguranca;
import br.com.timanager.servico.ServicoPrincipal;
import br.com.timanager.util.Utils;

@Named
@SessionScoped
public class ControladorHome implements Serializable {

	private static final long serialVersionUID = 7894371047005757055L;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	@Inject
	ServicoPrincipal servicoPrincipal;

	@Inject
	private Seguranca sec;

	private PieChartModel graficoItens;
	private PieChartModel graficoItensValores;
	private List<Peca> pecas = new ArrayList<Peca>();
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private List<Garantia> garantiasVencidas = new ArrayList<Garantia>();

	public void inicializar() {
		System.out.println("inicializando...controlador Home");
		if (FacesUtil.isNotPostback()) {
			preencherListagens();
			chamaNotificacao();
			if (this.pecas.size() != 0) {
				gerarGraficoItensPorEmpresa();
				gerarGraficoValoresItemPorEmpresa();
			}

		}

	}

	public void chamaNotificacao() {
		this.getNotificacao();
	}

	public String getNotificacao() {
		this.garantiasVencidas = servicoPrincipal.checarGarantiasVencidas();
		String msg = "Existe(em) " + this.garantiasVencidas.size()
				+ " garantia(as) vencida(as), verifique o relatório de garantias.";
		return msg;
	}

	public boolean isTemRegistros() {
		return CollectionUtils.isNotEmpty(this.garantiasVencidas);
	}

	public PieChartModel getGraficoItens() {
		return graficoItens;
	}

	public void setGraficoItens(PieChartModel graficoItens) {
		this.graficoItens = graficoItens;
	}

	private ControladorHome gerarGraficoValoresItemPorEmpresa() {
		this.graficoItensValores = new PieChartModel();

		for (Peca peca : this.pecas) {
			String texto = null;
			final BigDecimal valor = valores(peca.getEmpresa());

			texto = StringUtils.join(peca.getEmpresa().getNomeFantasia(), " (", "R$ ", Utils.formatarMoeda(valor), ")");

			this.graficoItensValores.set(texto, filtrarPorEmpresaValor(peca.getEmpresa()));
		}

		this.graficoItensValores.setTitle("GRÁFICO DE VALOR DE ITENS POR EMPRESA");
		this.graficoItensValores.setSeriesColors("E7E658,1a85ba,66cc66");
		this.graficoItensValores.setLegendPosition("w");
		this.graficoItensValores.setFill(true);
		this.graficoItensValores.setShowDataLabels(true);
		this.graficoItensValores.setDiameter(230);
		this.graficoItensValores.setDataFormat("percent");
		return this;
	}

	public BigDecimal valores(Empresa empresa) {
		BigDecimal valor = BigDecimal.ZERO;

		for (Peca peca : this.pecas) {
			if (peca.getEmpresa().equals(empresa)) {
				valor = valor.add(peca.getValor());
			}

		}
		return valor.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal filtrarPorEmpresaValor(Empresa empresa) {
		BigDecimal valor = BigDecimal.ZERO;

		for (Integer i = 0; i < this.pecas.size(); i++) {
			if (this.pecas.get(i).getEmpresa().equals(empresa)) {
				valor = valores(empresa);

			}
		}
		return valor;
	}

	public Integer filtrarPorEmpresa(Empresa empresa) {
		Integer count = 0;

		for (Integer i = 0; i < this.pecas.size(); i++) {
			if (this.pecas.get(i).getEmpresa().equals(empresa)) {
				count++;

			}
		}
		return count;
	}

	private ControladorHome gerarGraficoItensPorEmpresa() {

		this.graficoItens = new PieChartModel();

		for (final Peca peca : this.pecas) {
			this.graficoItens.set(peca.getEmpresa().getNomeFantasia(), this.filtrarPor(peca));
		}
		this.graficoItens.setTitle("GRÁFICO DE ITENS POR EMPRESA");
		this.graficoItens.setSeriesColors("E7E658,1a85ba,66cc66,FF7256 ,FFA500");
		this.graficoItens.setLegendPosition("w");
		this.graficoItens.setFill(true);
		this.graficoItens.setShowDataLabels(true);
		this.graficoItens.setDiameter(230);
		this.graficoItens.setDataFormat("value");
		return this;
	}

	public Integer filtrarPor(final Peca peca) {

		return CollectionUtils.countMatches(this.pecas, new Predicate<Peca>() {

			@Override
			public boolean evaluate(Peca registro) {

				return registro.getEmpresa().getNomeFantasia().equals(peca.getEmpresa().getNomeFantasia());
			}
		});
	}

	// public BigDecimal filtrarValores(List<Peca> registros) {
	//
	// BigDecimal valor = BigDecimal.ZERO;
	// for (Peca peca : CollectionUtils.select(registros,
	// this.getPredicado(registros))) {
	// valor = valor.add(peca.getValor());
	// }
	//
	// return valor.setScale(2, RoundingMode.HALF_EVEN);
	// }

	public Predicate<Peca> getPredicado(List<Peca> registro) {
		return new Predicate<Peca>() {

			@Override
			public boolean evaluate(Peca peca) {
				// return peca.getValor().compareTo(BigDecimal.ZERO) > 0;
				return peca.getEmpresa().equals(peca.getEmpresa());
			}
		};
	}

	// private Predicate<Peca> getPredicado(List<Peca> registros) {
	//
	// return new Predicate<Peca>() {
	//
	// @Override
	// public boolean evaluate(Peca registro) {
	//
	// return registro.getValor().compareTo();
	// }
	// };
	//
	// }
	//
	public void preencherListagens() {
		this.pecas = pecasDAO.buscarTodos();
		this.empresas = empresasDAO.buscarTodas();
	}

	public List<Peca> getPecas() {
		return pecas;
	}

	public void setPecas(List<Peca> pecas) {
		this.pecas = pecas;
	}

	public PieChartModel getGraficoItensValores() {
		return graficoItensValores;
	}

	public void setGraficoItensValores(PieChartModel graficoItensValores) {
		this.graficoItensValores = graficoItensValores;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public boolean isPossuiRegistros() {

		return CollectionUtils.isNotEmpty(this.pecas);
	}

	public List<Garantia> getGarantiasVencidas() {
		return garantiasVencidas;
	}

	public void setGarantiasVencidas(List<Garantia> garantiasVencidas) {
		this.garantiasVencidas = garantiasVencidas;
	}

	public List<Notificacao> getNotificacoesUsuario() {
		return this.servicoPrincipal.getNotificacoesUsuario(this.sec.getUsuarioLogado().getUsuario());
	}

	public Integer getNumeroDeNotificacoes() {
		return this.servicoPrincipal.getQuantidadeNotificaoesUsuario(this.sec.getUsuarioLogado().getUsuario());
	}

	public StreamedContent getFotoUsuarioLogado() {
		return this.servicoPrincipal.getFotoUsuarioLogado(this.sec.getUsuarioLogado().getUsuario());
	}

}
