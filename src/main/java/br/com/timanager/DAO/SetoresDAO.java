package br.com.timanager.DAO;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Setor;

public class SetoresDAO implements Serializable {

	private static final long serialVersionUID = -2840558283070718398L;

	@Inject
	private EntityManager manager;

	public Setor porId(Long id) {
		try {
			return manager.createQuery("from Setor where id = :id", Setor.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Setor guardar(Setor setor) {
		return manager.merge(setor);
	}

	public List<Setor> buscarTodos() {
		try {
			return manager.createQuery("from Setor ", Setor.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
