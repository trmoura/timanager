package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.ChamadosDAO;
import br.com.timanager.modelo.Chamado;

@FacesConverter(forClass = Chamado.class)
public class ConverterChamado implements Converter {

	@Inject
	private ChamadosDAO chamadosDAO;

	public ConverterChamado() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Chamado retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = chamadosDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Chamado chamado = (Chamado) value;
			return chamado.getId() == null ? null : chamado.getId().toString();
		}

		return "";
	}

}