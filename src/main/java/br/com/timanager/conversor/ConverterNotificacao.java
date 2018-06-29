package br.com.timanager.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.timanager.DAO.NotificacoesDAO;
import br.com.timanager.modelo.Notificacao;

@FacesConverter(forClass = Notificacao.class)
public class ConverterNotificacao implements Converter {

	@Inject
	private NotificacoesDAO notificacoesDAO;

	public ConverterNotificacao() {
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		Notificacao retorno = null;
		if (value == null || value.isEmpty()) {
			return retorno;
		} else {
			Long id = new Long(value);
			retorno = notificacoesDAO.porId(id);
			return retorno;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Notificacao not = (Notificacao) value;
			return not.getId() == null ? null : not.getId().toString();
		}

		return "";
	}

}