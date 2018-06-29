package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.jsf.FacesUtil;
import br.com.timanager.modelo.Usuario;

public class UsuariosDAO implements Serializable {

	private static final long serialVersionUID = -3672573200010736161L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		try {
			return manager.createQuery("from Usuario where id = :id", Usuario.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	public List<Usuario> buscarTodos() {
		try {
			return manager.createQuery("from Usuario Order by id DESC", Usuario.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Usuario> buscarUsuariosTecnico() {
		try {
			return manager.createQuery("from Usuario where cargo = 'TECNICO' ", Usuario.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario buscarGruposUsuarioPor(Long usuario) {
		return manager.createQuery("select u from Usuario u join fetch u.grupos where u.id = :usuario", Usuario.class)
				.setParameter("usuario", usuario).getSingleResult();
	}

	public Usuario porNome(String nome) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery("select u from Usuario u where lower(u.nome) = :nome", Usuario.class)
					.setParameter("nome", nome.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			FacesUtil.addInfoMessage("Nome ou Senha Inválidos");
		}

		return usuario;
	}

	public byte[] buscarFotoPor(Long id) {
		try {
			final StringBuilder JPQL = new StringBuilder("select u.foto from usuario u where u.id = :pId ");
			return (byte[]) manager.createNativeQuery(JPQL.toString(), Usuario.class).setParameter("pId", id)
					.getSingleResult();			
		}catch (NoResultException nre){
			return null;
		}
	}

}
