package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.GruposDAO;
import br.com.timanager.modelo.Grupo;

@FacesConverter(forClass = Grupo.class)
public class ConverterGrupo implements Converter {

	@Inject
	private GruposDAO gruposDAO;

	public ConverterGrupo() {

	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Grupo retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = gruposDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Grupo grupo = (Grupo) value;
			return grupo.getId() == null ? null : grupo.getId().toString();
		}

		return "";
	}

}