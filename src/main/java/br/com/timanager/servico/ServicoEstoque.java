package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.EstoquesDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Estoque;

public class ServicoEstoque implements Serializable {

	private static final long serialVersionUID = -1246034739628980066L;
	
	@Inject
	private EstoquesDAO estoques;

	@Transactional
	public Estoque salvar(Estoque estoque) {
		Estoque estoqueExistente = estoques.porId(estoque.getId());
		if (estoqueExistente != null && !estoqueExistente.equals(estoque)) {
			throw new NegocioException("Já existe uma registro com o id informado.");
		}

		return estoques.guardar(estoque);
	}

}