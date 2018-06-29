package br.com.timanager.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.Predicate;

@SuppressWarnings("rawtypes")
public class MeuPredicado implements Predicate {

	private Object objetoEsperado;
	private String propriedade;

	public MeuPredicado(String propriedade, Object objetoEsperado) {
		super();
		this.propriedade = propriedade;
		this.objetoEsperado = objetoEsperado;
	}

	public boolean evaluate(Object object) {
		try {
			return objetoEsperado.equals(PropertyUtils.getProperty(object, propriedade));
		} catch (Exception e) {
			return false;
		}
	}

}