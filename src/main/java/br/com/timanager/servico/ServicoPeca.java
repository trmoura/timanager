package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.PecasDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Peca;


public class ServicoPeca implements Serializable {

	private static final long serialVersionUID = 8396814264798413542L;
	
	@Inject
	private PecasDAO pecas;

	@Transactional
	public Peca salvar(Peca peca) {
		Peca pecaExistente = pecas.porId(peca.getId());
		if (pecaExistente != null && !pecaExistente.equals(peca)) {
			throw new NegocioException("Já existe uma Peça com o id informado.");
		}

		if (peca.getEmpresa() == null) {
			throw new NegocioException("Selecione uma Empresa.");
		}

		return pecas.guardar(peca);
	}

}