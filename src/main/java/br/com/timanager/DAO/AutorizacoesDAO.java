package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Autorizacao;

public class AutorizacoesDAO implements Serializable {

	private static final long serialVersionUID = 8078517741245250225L;

	@Inject
	private EntityManager manager;

	public Autorizacao porId(Long id) {
		try {
			return manager.createQuery("from Autorizacao where id = :id", Autorizacao.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Autorizacao porAlias(String alias) {
		try {
			return manager.createQuery("from Autorizacao where alias = :alias", Autorizacao.class)
					.setParameter("alias", alias).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Autorizacao> buscarTodas() {
		return manager.createQuery("from Autorizacao", Autorizacao.class).getResultList();
	}

	// select u from User u left join fetch u.roles
	public List<Autorizacao> buscarAutorizacoesGrupo(Long id) {
		return manager
				.createNativeQuery("SELECT a.* FROM autorizacao a INNER JOIN grupo_autorizacao ga ON ga.AUT_ID = a.id "
						+ "WHERE ga.GRUPO_ID = :id", Autorizacao.class)
				.setParameter("id", id).getResultList();
	}

	public Autorizacao guardar(Autorizacao autorizacao) {
		return manager.merge(autorizacao);
	}

	@Transactional
	public void remover(Autorizacao autorizacao) {
		try {
			autorizacao = porId(autorizacao.getId());
			if (autorizacao != null) {
				manager.remove(autorizacao);
				manager.flush();
			}
		} catch (PersistenceException e) {
			throw new NegocioException(
					"Autorizacao não pode ser excluída, verifique se não está sendo usado em outra rotinha.");
		}

	}

	public Autorizacao getROLEManutencaoChamado() {

		return this.porAlias("Manutenção chamados");
	}

}
