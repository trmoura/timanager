package br.com.timanager.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.ToggleEvent;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.VO.ConsultaMapaEquipamentos;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Setor;

@Named
@ViewScoped
public class ControladorMapaEquipamentos implements Serializable {

	private static final long serialVersionUID = 1095189890509998536L;

	@Inject
	private PecasDAO pecasDAO;

	@Inject
	private EmpresasDAO empresasDAO;

	private Empresa empresa;

	private List<ConsultaMapaEquipamentos> mapa;

	private List<Empresa> empresas;

	private List<Setor> setores;

	public ControladorMapaEquipamentos() {

	}

	@PostConstruct
	public void inicializar() {
		preencherListagem();
	}

	public void preencherListagem() {
		this.empresas = empresasDAO.buscarTodas();
	}

	public void pesquisar() {
		this.mapa = pecasDAO.consultaMapaEquipamentos(this.empresa);
	}

//	public void localTipoSelecionado(ActionEvent actionEvent) {
//
//		Long idTipo = (Long) actionEvent.getComponent().getAttributes().get("idTipo");
//		this.setores = pecasDAO.consultaLocalPor(idTipo);
//
//	}
	
	public List<Setor> onRowToggle(ToggleEvent event) {

		this.setores = new ArrayList<Setor>();
		ConsultaMapaEquipamentos vo = (ConsultaMapaEquipamentos) event.getData();
		return this.setores = pecasDAO.consultaLocalPor(vo);
	}

	public List<ConsultaMapaEquipamentos> getMapa() {
		return mapa;
	}

	public void setMapa(List<ConsultaMapaEquipamentos> mapa) {
		this.mapa = mapa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

}
