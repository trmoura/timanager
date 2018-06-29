package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.timanager.modelo.Chamado;
import br.com.timanager.modelo.Usuario;
import br.com.timanager.util.DataUtil;

public class ChamadosDAO implements Serializable {

	private static final long serialVersionUID = 4837173743587416382L;

	@Inject
	private EntityManager manager;

	public Chamado porId(Long id) {
		try {
			return manager.createQuery("from Chamado where id = :id", Chamado.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Chamado guardar(Chamado chamado) {
		return manager.merge(chamado);
	}

	public List<Chamado> buscarTodos() {
		try {
			return manager.createQuery("from Chamado ", Chamado.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Chamado> buscarPorData(Date dataInicial, Date dataFinal) {
		try {
			return manager
					.createQuery(
							"from Chamado c where c.dataAbertura >= :dataInicial And c.dataAbertura <= :dataFinal and c.tecnico like :tecnico ",
							Chamado.class)
					.setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Chamado> buscarChamadosPor(Date dataInicial, Date dataFinal, Usuario tecnico) {

		final StringBuilder SQL = new StringBuilder(" SELECT  c.* FROM chamado c  ");

		if (dataInicial == null && dataFinal == null && tecnico == null) {
			return null;

		}

		if (dataInicial != null && dataFinal == null) {
			return null;

		}
		if (dataInicial == null && dataFinal != null) {
			return null;

		}

		if (dataInicial == null && dataFinal == null) {
			return null;

		}

		if (dataInicial != null && dataFinal != null) {
			SQL.append("WHERE 1=1 AND c.dataAbertura >= " + "'" + DataUtil.formatoBanco(dataInicial) + "'"
					+ " AND c.dataAbertura <= " + "'" + DataUtil.formatoBanco(dataFinal) + "'");

		}

		if (tecnico != null) {
			SQL.append(" AND c.tecnico_id = " + tecnico.getId());

		}

		SQL.append(" ORDER BY c.dataAbertura DESC ");

		final Query query = manager.createNativeQuery(SQL.toString(), Chamado.class);

		return query.getResultList();
	}

}
