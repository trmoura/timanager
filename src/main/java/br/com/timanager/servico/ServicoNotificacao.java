package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.NotificacoesDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Notificacao;

public class ServicoNotificacao implements Serializable {

	private static final long serialVersionUID = 691807836861598952L;

	@Inject
	private NotificacoesDAO noticacoes;

	@Transactional
	public Notificacao salvar(Notificacao not) {
		Notificacao notExistente = noticacoes.porId(not.getId());
		if (notExistente != null && !notExistente.equals(not)) {
			throw new NegocioException("Já existe uma noticação com o id informado.");
		}

		return noticacoes.guardar(not);
	}

}