package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Item;

public class ItensDAO implements Serializable {


	private static final long serialVersionUID = -6591284725510227499L;
	
	
	@Inject
	private EntityManager manager;

	public Item porId(Long id) {
		try {
			return manager.createQuery("from Item where id = :id", Item.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Item guardar(Item item) {
		return manager.merge(item);
	}

	public List<Item> buscarTodos() {
		try {
			return manager.createQuery("from Item ", Item.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
