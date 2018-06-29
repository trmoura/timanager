package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Marca;

public class MarcasDAO implements Serializable {

	private static final long serialVersionUID = -2840558283070718398L;

	@Inject
	private EntityManager manager;

	public Marca porId(Long id) {
		try {
			return manager.createQuery("from Marca where id = :id", Marca.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Marca guardar(Marca marca) {
		return manager.merge(marca);
	}

	public List<Marca> buscarTodos() {
		try {
			return manager.createQuery("from Marca ", Marca.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
