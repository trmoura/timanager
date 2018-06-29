package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.timanager.DAO.GruposDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Grupo;

public class ServicoGrupo implements Serializable {

	private static final long serialVersionUID = -7746591255595892207L;
	
	@Inject
	private GruposDAO grupos;

	
	public Grupo salvar(Grupo grupo) {
		Grupo grupoExistente = grupos.porId(grupo.getId());

		if (grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioException("Já existe um registro com o id informado.");
		}

		return grupos.guardar(grupo);
	}

}