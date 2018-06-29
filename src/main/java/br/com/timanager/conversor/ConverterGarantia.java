package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.GarantiasDAO;
import br.com.timanager.modelo.Garantia;

@FacesConverter(forClass = Garantia.class)
public class ConverterGarantia implements Converter {

	@Inject
	private GarantiasDAO garantiasDAO;

	public ConverterGarantia() {
		
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Garantia retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = garantiasDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Garantia garantia = (Garantia) value;
			return garantia.getId() == null ? null : garantia.getId().toString();
		}

		return "";
	}

}