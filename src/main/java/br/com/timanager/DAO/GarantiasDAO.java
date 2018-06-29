package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Garantia;
import br.com.timanager.modelo.GarantiaDetalhe;
import br.com.timanager.modelo.HistoricoItemGarantia;

public class GarantiasDAO implements Serializable {

	private static final long serialVersionUID = -830371002457983809L;

	@Inject
	private EntityManager manager;

	public Garantia porId(Long id) {
		try {
			return manager.createQuery("from Garantia where id = :id", Garantia.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public GarantiaDetalhe detalheGarantiaPorId(Long id) {
		try {
			return manager.createQuery("from GarantiaDetalhe where id = :id", GarantiaDetalhe.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Garantia guardar(Garantia garantia) {
		return manager.merge(garantia);
	}

	public GarantiaDetalhe guardarGarantiaDetalhe(GarantiaDetalhe garantiadetalhe) {
		return manager.merge(garantiadetalhe);
	}

	public HistoricoItemGarantia guardarHistoricoDetalhe(HistoricoItemGarantia historicoItemGarantia) {
		return manager.merge(historicoItemGarantia);
	}

	public List<Garantia> buscarTodas() {
		try {
			return manager.createQuery("from Garantia ", Garantia.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Garantia> buscarPorData(Date dataInicial, Date dataFinal) {
		try {
			return manager
					.createQuery(
							"from Garantia g where g.dataCadastro >= :dataInicial And g.dataCadastro <= :dataFinal  ",
							Garantia.class)
					.setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<HistoricoItemGarantia> buscarHistoricosPor(GarantiaDetalhe detalhe) {
		try {
			return manager.createQuery("from HistoricoItemGarantia h where h.garantiaDetalhe = :detalhe",
					HistoricoItemGarantia.class).setParameter("detalhe", detalhe).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Garantia> garantiasVencidas() {
		return manager.createNativeQuery(
				"SELECT * FROM garantia WHERE dataFinal < DATE(NOW()) AND situacao = 'VENCIDA' AND baixada = 'N'",
				Garantia.class).getResultList();
	}

	public List<Garantia> buscarGarantiasPor(Empresa empresa, DominioSituacaoGarantia situacao, DominioSimNao baixada) {

		final StringBuilder SQL = new StringBuilder(" SELECT  G.* FROM garantia G  ");

		if (empresa == null && situacao == null && baixada == null) {
			return null;

		}

		if (empresa != null) {
			SQL.append("WHERE 1=1 AND G.empresa_id = " + empresa.getId());

		}

		if (situacao != null) {
			SQL.append(" AND (G.situacao LIKE " + "'" + situacao.name() + "%' ) ");

		}
		
		if (baixada != null) {
			SQL.append(" AND (G.baixada LIKE " + "'" + baixada.name() + "%' ) ");
			
		}

		SQL.append(" ORDER BY G.dataFinal ASC ");

		final Query query = manager.createNativeQuery(SQL.toString(), Garantia.class);

		return query.getResultList();
	}

}
