package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Empresa;


public class EmpresasDAO implements Serializable {


	private static final long serialVersionUID = 2436032876816595553L;
	
	@Inject
	private EntityManager manager;

	public Empresa porId(Long id) {
		try {
			return manager.createQuery("from Empresa where id = :id", Empresa.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Empresa guardar(Empresa empresa) {
		return manager.merge(empresa);
	}

	public List<Empresa> buscarTodas() {
		try {
			return manager.createQuery("from Empresa ", Empresa.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
