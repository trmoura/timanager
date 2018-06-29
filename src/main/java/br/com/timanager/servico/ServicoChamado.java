package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.ChamadosDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Chamado;

public class ServicoChamado implements Serializable {

	private static final long serialVersionUID = -8279648528503673591L;

	@Inject
	private ChamadosDAO chamados;

	@Transactional
	public Chamado salvar(Chamado chamado) {
		Chamado chamadoExistente = chamados.porId(chamado.getId());
		if (chamadoExistente != null && !chamadoExistente.equals(chamado)) {
			throw new NegocioException("Já existe uma registro com o id informado.");
		}

		return chamados.guardar(chamado);
	}

}