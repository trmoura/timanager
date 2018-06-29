package br.com.timanager.DAO;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.timanager.modelo.ItemNotaEntrada;
import br.com.timanager.modelo.NotaFiscalEntrada;

public class NotasEntradaDAO implements Serializable {

	private static final long serialVersionUID = 2318507776557926036L;

	@Inject
	private EntityManager manager;

	public NotaFiscalEntrada porId(Long id) {
		try {
			return manager.createQuery("from NotaFiscalEntrada where id = :id", NotaFiscalEntrada.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ItemNotaEntrada ItemPorId(Long id) {
		try {
			return manager.createQuery("from ItemNotaEntrada where id = :id", ItemNotaEntrada.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public NotaFiscalEntrada guardar(NotaFiscalEntrada nota) {
		return manager.merge(nota);
	}

	public ItemNotaEntrada guardarItemNota(ItemNotaEntrada item) {
		return manager.merge(item);
	}

	public List<NotaFiscalEntrada> buscarTodas() {
		try {
			return manager.createQuery("from NotaFiscalEntrada ", NotaFiscalEntrada.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<ItemNotaEntrada> buscarTodos() {
		try {
			return manager.createQuery("from ItemNotaEntrada ", ItemNotaEntrada.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<ItemNotaEntrada> buscarTodosPor(NotaFiscalEntrada nota) {
		try {
			return manager.createQuery("from ItemNotaEntrada WHERE notaFiscalEntrada =: nota", ItemNotaEntrada.class)
					.setParameter("nota", nota).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<NotaFiscalEntrada> buscarPorData(Date dataInicial, Date dataFinal) {
		try {
			return manager
					.createQuery(
							"from NotaFiscalEntrada n where n.dataEmissao >= :dataInicial And n.dataEmissao <= :dataFinal  ",
							NotaFiscalEntrada.class)
					.setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
