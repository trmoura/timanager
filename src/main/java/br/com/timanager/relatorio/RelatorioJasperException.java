package br.com.timanager.relatorio;

import java.io.Serializable;

import br.com.timanager.jsf.NegocioException;

public class RelatorioJasperException implements Serializable {

	private static final long serialVersionUID = 747886198051827951L;

	public RelatorioJasperException() {
		throw new NegocioException("Existem Erros no Arquivo Jasper.");
	}

}