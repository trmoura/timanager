package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.TiposItemDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.TipoItem;

public class ServicoTipoItem implements Serializable {

	private static final long serialVersionUID = 5207333905273632617L;

	@Inject
	private TiposItemDAO tipos;

	@Transactional
	public TipoItem salvar(TipoItem tipo) {
		TipoItem tipoExistente = tipos.porId(tipo.getId());
		if (tipoExistente != null && !tipoExistente.equals(tipo)) {
			throw new NegocioException("Já existe uma Registro com o id informado.");
		}

		return tipos.guardar(tipo);
	}

}