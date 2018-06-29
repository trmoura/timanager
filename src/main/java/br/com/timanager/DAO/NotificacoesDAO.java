package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Notificacao;
import br.com.timanager.modelo.Usuario;

public class NotificacoesDAO implements Serializable {

	private static final long serialVersionUID = 288215098781886303L;

	@Inject
	private EntityManager manager;

	public Notificacao porId(Long id) {
		try {
			return manager.createQuery("from Notificacao where id = :id", Notificacao.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Notificacao guardar(Notificacao notificacao) {
		return manager.merge(notificacao);
	}

	public List<Notificacao> buscarTodas() {
		try {
			return manager.createQuery("from Notificacao ", Notificacao.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Notificacao> buscarNotificacoesPor(Usuario usuario) {
		try {
			return manager.createQuery("select n from Notificacao n where n.usuario = :usuario order by n.dataNotificacao desc",
					Notificacao.class).setParameter("usuario", usuario).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
