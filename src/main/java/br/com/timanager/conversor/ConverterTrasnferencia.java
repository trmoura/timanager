package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.TransferenciasDAO;
import br.com.timanager.modelo.Transferencia;

@FacesConverter(forClass = Transferencia.class)
public class ConverterTrasnferencia implements Converter {

	@Inject
	private TransferenciasDAO trasnferenciasDAO;

	public ConverterTrasnferencia() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Transferencia retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = trasnferenciasDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Transferencia transferencia = (Transferencia) value;
			return transferencia.getId() == null ? null : transferencia.getId().toString();
		}

		return "";
	}

}