package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.EmpresasDAO;
import br.com.timanager.modelo.Empresa;

@FacesConverter(forClass = Empresa.class)
public class ConverterEmpresa implements Converter {

	@Inject
	private EmpresasDAO empresasDAO;

	public ConverterEmpresa() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Empresa retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = empresasDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Empresa empresa = (Empresa) value;
			return empresa.getId() == null ? null : empresa.getId().toString();
		}

		return "";
	}

}