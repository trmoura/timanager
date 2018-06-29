package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.SetoresDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Setor;

public class ServicoSetor implements Serializable {

	private static final long serialVersionUID = 2900295993630436391L;

	@Inject
	private SetoresDAO setores;

	@Transactional
	public Setor salvar(Setor setor) {
		Setor setorExistente = setores.porId(setor.getId());
		if (setorExistente != null && !setorExistente.equals(setor)) {
			throw new NegocioException("Já existe um Registro com o id informado.");
		}

		return setores.guardar(setor);
	}

}