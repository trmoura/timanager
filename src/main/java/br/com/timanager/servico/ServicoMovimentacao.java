package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.MovimentacoesDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Movimentacao;

public class ServicoMovimentacao implements Serializable {

	private static final long serialVersionUID = -8769801175623922846L;

	@Inject
	private MovimentacoesDAO movs;

	@Transactional
	public Movimentacao salvar(Movimentacao mov) {
		Movimentacao movExistente = movs.porId(mov.getId());
		if (movExistente != null && !movExistente.equals(mov)) {
			throw new NegocioException("Já existe uma registro com o id informado.");
		}

		return movs.guardar(mov);
	}

}