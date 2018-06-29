package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.qlrm.mapper.JpaResultMapper;

import br.com.timanager.VO.ConsultaMapaEquipamentos;
import br.com.timanager.VO.PecasEstoqueVisualizacao;
import br.com.timanager.VO.RelatorioSinteticoVisualizacao;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Peca;
import br.com.timanager.modelo.Setor;

public class PecasDAO implements Serializable {

	private static final long serialVersionUID = -1110416068917402093L;

	@Inject
	private EntityManager manager;

	public Peca porId(Long id) {
		try {
			return manager.createQuery("from Peca where id = :id", Peca.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Peca guardar(Peca peca) {
		return manager.merge(peca);
	}

	public List<Peca> buscarTodos() {
		try {
			return manager.createQuery("from Peca ", Peca.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Peca> buscarPecasPorEmpresa(Empresa empresa) {
		final StringBuilder SQL = new StringBuilder(
				" SELECT P.*, P.id as \"id\", P.descricao as \"descricao\", S.descricao AS \"setor\" FROM peca P  ");

		SQL.append("INNER JOIN setor S ON P.setor_id = S.id WHERE 1=1 ");

		if (empresa != null) {
			SQL.append(" AND P.empresa_id = " + empresa.getId());
		} else {
			return null;
		}

		SQL.append(" GROUP BY setor, P.id, P.descricao");

		final Query query = manager.createNativeQuery(SQL.toString(), Peca.class);

		return query.getResultList();
	}

	public List<RelatorioSinteticoVisualizacao> buscarTotalPecas(Empresa empresa) {

		final StringBuilder SQL = new StringBuilder(
				" SELECT SUM(total_count) AS \"total\", SUM(total_manu_count) AS \"total_manu\", tipo as tipoItem, empresa as empresaItem, descricao FROM (  ");

		SQL.append(
				" SELECT  COUNT(P.id) AS total_count, 0 AS total_manu_count, T.descricao AS tipo, E.nomeFantasia as empresa, P.descricao as descricao FROM peca P ");

		SQL.append(" INNER JOIN tipo_de_item T ON T.id = P.tipo_id ");

		SQL.append(" INNER JOIN empresa E ON E.id = P.empresa_id ");

		SQL.append(" WHERE P.empresa_id = " + empresa.getId() + " GROUP BY P.descricao ");

		SQL.append(" UNION");

		SQL.append(
				" SELECT  0 AS total_count, COUNT(P.em_manutencao) AS total_manu_count, T.descricao AS tipo, E.nomeFantasia as empresa, P.descricao as descricao FROM peca P ");

		SQL.append(" INNER JOIN tipo_de_item T ON T.id = P.tipo_id ");

		SQL.append(" INNER JOIN empresa E ON E.id = P.empresa_id ");

		SQL.append(" WHERE P.empresa_id = " + empresa.getId() + " AND P.em_manutencao = 'S' ");

		SQL.append(" GROUP BY P.descricao) TMP GROUP BY descricao ");

		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		final Query query = manager.createNativeQuery(SQL.toString());
		List<RelatorioSinteticoVisualizacao> list = jpaResultMapper.list(query, RelatorioSinteticoVisualizacao.class);

		return list;
	}

	public List<Peca> buscarPorDescricao(String filtro, Long empresa) {

		final StringBuilder SQL = new StringBuilder(" SELECT  P.* FROM peca P  ");

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append("WHERE 1=1 AND P.empresa_id = " + empresa);

		}

		if (StringUtils.isBlank(filtro) || StringUtils.isEmpty(filtro)) {
			return null;

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.DESCRICAO LIKE " + "'" + filtro + "%' ) ");

		}
		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.DESCRICAO LIKE " + "'" + filtro + "%' ) ");

		}
		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.DESCRICAO LIKE " + "'" + filtro + "%' ) ");

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" OR (P.NUMEROPATRIMONIO LIKE " + "'" + filtro + "%' ) ");

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" OR (P.NUMEROSERIE LIKE " + "'" + filtro + "%' ) ");

		}

		SQL.append(" ORDER BY P.DESCRICAO ASC ");

		final Query query = manager.createNativeQuery(SQL.toString(), Peca.class);

		return query.getResultList();
	}

	public List<PecasEstoqueVisualizacao> buscarPecaEstoque(String filtro, Long empresa) {

		final StringBuilder SQL = new StringBuilder(
				" SELECT P.id AS \"id\", P.descricao AS \"descricao\" ,P.numeroSerie AS \"numero\", ");

		SQL.append(
				" P.numeroPatrimonio AS \"patrimonio\", COALESCE(E.quantidade, 0) AS \"quantidade\", P.empresa_id as \"idEmpresa\", ");

		SQL.append(" EP.nomeFantasia AS \"empresa\" FROM peca P LEFT JOIN estoque ");

		SQL.append(" E ON E.peca_id = P.id INNER JOIN empresa EP ON EP.id = P.empresa_id ");

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append("WHERE 1=1 AND E.quantidade > 0 AND E.empresa_id = " + empresa);

		}

		if (StringUtils.isBlank(filtro) || StringUtils.isEmpty(filtro)) {
			return null;

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.descricao LIKE " + "'" + filtro + "%' ) ");

		}
		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.descricao LIKE " + "'" + filtro + "%' ) ");

		}
		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" AND (P.descricao LIKE " + "'" + filtro + "%' ) ");

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" OR (P.numeropatrimonio LIKE " + "'" + filtro + "%' ) ");

		}

		if (StringUtils.isNotBlank(filtro) && StringUtils.isNotEmpty(filtro)) {
			SQL.append(" OR (P.numeroserie LIKE " + "'" + filtro + "%' ) ");

		}

		SQL.append(" ORDER BY P.descricao ASC ");

		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		final Query query = manager.createNativeQuery(SQL.toString());
		List<PecasEstoqueVisualizacao> list = jpaResultMapper.list(query, PecasEstoqueVisualizacao.class);

		return list;
	}

	public List<ConsultaMapaEquipamentos> consultaMapaEquipamentos(Empresa empresa) {

		final StringBuilder SQL = new StringBuilder(
				" SELECT t.id as \"id\" ,t.descricao as \"descricao\", COUNT(p.id) AS \"qtd\" FROM tipo_de_item t ");

		SQL.append("INNER JOIN peca p ON p.tipo_id = t.id ");

		if (empresa != null) {
			SQL.append("WHERE p.empresa_id = " + empresa.getId());
		}

		SQL.append(" GROUP BY t.descricao ");

		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		final Query query = manager.createNativeQuery(SQL.toString());
		List<ConsultaMapaEquipamentos> list = jpaResultMapper.list(query, ConsultaMapaEquipamentos.class);

		return list;

	}

	public List<Setor> consultaLocalPor(ConsultaMapaEquipamentos vo) {

		final StringBuilder SQL = new StringBuilder(" SELECT s.*, t.descricao FROM setor s ");

		SQL.append("INNER JOIN peca p ON p.setor_id = s.id ");
		SQL.append("INNER JOIN tipo_de_item t ON t.id = p.tipo_id ");

		if (vo != null) {
			SQL.append("WHERE p.tipo_id = " + vo.getId());
		}

		final Query query = manager.createNativeQuery(SQL.toString(), Setor.class);

		return query.getResultList();

	}

}
