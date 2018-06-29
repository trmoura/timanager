package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.dominio.DominioParametroSistema;
import br.com.timanager.modelo.ParametroSistema;

public class ParametrosDAO implements Serializable {

	private static final long serialVersionUID = -5736548199673014700L;

	@Inject
	private EntityManager manager;

	public ParametroSistema buscarPor(DominioParametroSistema parametro) {
		try {
			return manager.createQuery("from ParametroSistema where nomeParametro = :parametro", ParametroSistema.class)
					.setParameter("parametro", parametro).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ParametroSistema getParametroURLSistema() {

		return this.buscarPor(DominioParametroSistema.URL_SISTEMA);
	}

	public ParametroSistema getParametroURLSistemaLocal() {

		return this.buscarPor(DominioParametroSistema.URL_SISTEMA_LOCAL);
	}

	public ParametroSistema getParametroNomeDoBancoDeDados() {

		return this.buscarPor(DominioParametroSistema.NOME_DO_BANCO_DE_DADOS);
	}

	public ParametroSistema getParametroLocalHost() {

		return this.buscarPor(DominioParametroSistema.LOCALHOST);
	}

	public ParametroSistema getParametroCaminhoBancoLocal() {

		return this.buscarPor(DominioParametroSistema.URL_BANCO_DADOS_LOCAL);
	}

	public ParametroSistema getParametroCaminhoBancoProducao() {
		return this.buscarPor(DominioParametroSistema.URL_BANCO_DADOS_PRODUCAO);
	}

	public ParametroSistema getParametroUsuarioLocal() {

		return this.buscarPor(DominioParametroSistema.USUARIO_BANCO_LOCAL);
	}

	public ParametroSistema getParametroUsuarioProducao() {

		return this.buscarPor(DominioParametroSistema.USUARIO_BANCO_PRODUCAO);
	}

	public ParametroSistema getParametroSenhaProducao() {

		return this.buscarPor(DominioParametroSistema.SENHA_BANCO_PRODUCAO);
	}

	public ParametroSistema getParametroSenhaLocal() {

		return this.buscarPor(DominioParametroSistema.SENHA_BANCO_LOCAL);
	}

	public ParametroSistema getUsuarioRemetente() {

		return this.buscarPor(DominioParametroSistema.USUARIO_EMAIL);
	}

	public ParametroSistema getServidorSMTP() {

		return this.buscarPor(DominioParametroSistema.SERVIDOR_SMTP);
	}

	public ParametroSistema getSenhaEMAIL() {

		return this.buscarPor(DominioParametroSistema.SENHA_EMAIL);
	}

	public ParametroSistema getPortaSMTP() {
		return this.buscarPor(DominioParametroSistema.PORTA_SMTP);
	}

	public List<ParametroSistema> buscarTodos() {
		try {
			return manager.createQuery("from ParametroSistema ", ParametroSistema.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
