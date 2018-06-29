package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.MarcasDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Marca;

public class ServicoMarca implements Serializable {

	private static final long serialVersionUID = 46966241695987632L;

	@Inject
	private MarcasDAO marcas;

	@Transactional
	public Marca salvar(Marca marca) {
		Marca marcaExistente = marcas.porId(marca.getId());
		if (marcaExistente != null && !marcaExistente.equals(marca)) {
			throw new NegocioException("Já existe uma Marca com o id informado.");
		}

		return marcas.guardar(marca);
	}

}