package br.com.timanager.DAO;


import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.TipoItem;

public class TiposItemDAO implements Serializable {


	private static final long serialVersionUID = -7501947130412248838L;
	
	@Inject
	private EntityManager manager;

	public TipoItem porId(Long id) {
		try {
			return manager.createQuery("from TipoItem where id = :id", TipoItem.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public TipoItem guardar(TipoItem tipoItem) {
		return manager.merge(tipoItem);
	}

	public List<TipoItem> buscarTodos() {
		try {
			return manager.createQuery("from TipoItem ", TipoItem.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
