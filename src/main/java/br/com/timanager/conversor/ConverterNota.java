package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.NotasEntradaDAO;
import br.com.timanager.modelo.NotaFiscalEntrada;

@FacesConverter(forClass = NotaFiscalEntrada.class)
public class ConverterNota implements Converter {

	@Inject
	private NotasEntradaDAO notasDAO;

	public ConverterNota() {

	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		NotaFiscalEntrada retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = notasDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			NotaFiscalEntrada nota = (NotaFiscalEntrada) value;
			return nota.getId() == null ? null : nota.getId().toString();
		}

		return "";
	}

}