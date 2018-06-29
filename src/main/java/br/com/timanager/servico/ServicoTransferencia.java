package br.com.timanager.servico;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.timanager.DAO.TransferenciasDAO;
import br.com.timanager.jsf.NegocioException;
import br.com.timanager.modelo.Transferencia;
import br.com.timanager.modelo.TransferenciaDetalhe;

public class ServicoTransferencia implements Serializable {

	private static final long serialVersionUID = -5277550475847288503L;

	@Inject
	private TransferenciasDAO transferencias;

	@Transactional
	public Transferencia salvar(Transferencia t) {
		Transferencia transferenciaExistente = transferencias.porId(t.getId());
		if (transferenciaExistente != null && !transferenciaExistente.equals(t)) {
			throw new NegocioException("Já existe uma Registro com o id informado.");
		}

		return transferencias.guardar(t);
	}

	@Transactional
	public TransferenciaDetalhe salvar(TransferenciaDetalhe td) {
		TransferenciaDetalhe transferenciadetalheExistente = transferencias.detalheTransferenciaPorId(td.getId());
		if (transferenciadetalheExistente != null && !transferenciadetalheExistente.equals(td)) {
			throw new NegocioException("Já existe uma Registro com o id informado.");
		}

		return transferencias.guardarTransferenciaDetalhe(td);
	}
}