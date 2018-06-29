package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.NotasEntradaDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.NotaFiscalEntrada;

public class ServicoNotaEntrada implements Serializable {

	private static final long serialVersionUID = -387225143760619659L;
	@Inject
	private NotasEntradaDAO notas;

	@Transactional
	public NotaFiscalEntrada salvar(NotaFiscalEntrada nota) {
		NotaFiscalEntrada notaExistente = notas.porId(nota.getId());
		if (notaExistente != null && !notaExistente.equals(nota)) {
			throw new NegocioException("Já existe um registro com o id informado.");
		}

		return notas.guardar(nota);
	}

}