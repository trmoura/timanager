package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.Transferencia;
import br.com.timanager.modelo.TransferenciaDetalhe;

public class TransferenciasDAO implements Serializable {

	private static final long serialVersionUID = 9105415106598182729L;

	@Inject
	private EntityManager manager;

	public Transferencia porId(Long id) {
		try {
			return manager.createQuery("from Transferencia where id = :id", Transferencia.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public TransferenciaDetalhe detalheTransferenciaPorId(Long id) {
		try {
			return manager.createQuery("from TransferenciaDetalhe where id = :id", TransferenciaDetalhe.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Transferencia guardar(Transferencia transferencia) {
		return manager.merge(transferencia);
	}

	public TransferenciaDetalhe guardarTransferenciaDetalhe(TransferenciaDetalhe transferenciadetalhe) {
		return manager.merge(transferenciadetalhe);
	}

	public List<Transferencia> buscarTodas() {
		try {
			return manager.createQuery("from Transferencia ", Transferencia.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Transferencia> buscarPorData(Date dataInicial, Date dataFinal) {
		try {
			return manager
					.createQuery(
							"from Transferencia t where t.dataCadastro >= :dataInicial And t.dataCadastro <= :dataFinal  ",
							Transferencia.class)
					.setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
