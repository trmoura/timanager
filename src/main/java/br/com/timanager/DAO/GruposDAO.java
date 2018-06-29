package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Grupo;

public class GruposDAO implements Serializable {


	private static final long serialVersionUID = 7576001055669252500L;
	
	@Inject
	private EntityManager manager;

	public Grupo porId(Long id) {
		try {
			return manager.createQuery("from Grupo where id = :id", Grupo.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Grupo> SelecionaGrupo(Grupo grupo) {
		return manager.createQuery("from Grupo where id = :id", Grupo.class).setParameter("id", grupo).getResultList();
	}

	public List<Grupo> grupos() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
	

	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}

	@Transactional
	public void remover(Grupo grupo) {
		try {
			grupo = porId(grupo.getId());
			if (grupo != null) {
				manager.remove(grupo);
				manager.flush();
			}
		} catch (PersistenceException e) {
			throw new NegocioException(
					"Grupo não pode ser excluído, verifique se não está sendo usado em outra rotinha.");
		}

	}

}
