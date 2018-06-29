package br.com.timanager.DAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;

import br.com.timanager.VO.MovimentacaoEstoqueVisualizacao;
import br.com.timanager.modelo.Empresa;
import br.com.timanager.modelo.Movimentacao;
import br.com.timanager.modelo.Peca;

public class MovimentacoesDAO implements Serializable {

	private static final long serialVersionUID = 1235980881900436479L;

	@Inject
	private EntityManager manager;

	public Movimentacao porId(Long id) {
		try {
			return manager.createQuery("from Movimentacao where id = :id", Movimentacao.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Movimentacao porPeca(Peca peca) {
		try {
			return manager.createQuery("from Movimentacao where pecaMovimentacao = :peca", Movimentacao.class)
					.setParameter("peca", peca).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Movimentacao guardar(Movimentacao movimentacao) {
		return manager.merge(movimentacao);
	}

	public List<Movimentacao> buscarTodas() {
		try {
			return manager.createQuery("from Movimentacao ", Movimentacao.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<MovimentacaoEstoqueVisualizacao> buscarMovimentacaoPor(Empresa empresa, Peca peca) {
		final StringBuilder SQL = new StringBuilder(
				"SELECT empresa, idPeca, descricao ,sequencia, dataMov, entrada, qtd_entrada, saida ,qtd_saida, origem  FROM ( ");

		SQL.append(
				"SELECT m.empresa_entrada AS \"empresa\", p.id AS \"idPeca\", p.descricao AS \"descricao\" ,m.id AS \"sequencia\", m.data_movimentacao AS \"dataMov\", m.quantidade AS \"qtd_entrada\", ");

		SQL.append(
				"0 AS \"qtd_saida\", m.tipo_movimentacao AS \"entrada\", '' AS \"saida\", m.origem_movimentacao AS \"origem\" FROM movimentacao_estoque m ");

		SQL.append("INNER JOIN peca p ON m.peca_movimentacao = p.id  ");

		SQL.append("WHERE m.tipo_movimentacao = 'ENTRADA' ");

		SQL.append("UNION ALL ");

		SQL.append(
				"SELECT m.empresa_saida AS \"empresa\", p.id AS \"idPeca\", p.descricao AS \"descricao\" ,m.id AS \"sequencia\", m.data_movimentacao AS \"dataMov\", 0 AS \"qtd_entrada\", ");

		SQL.append(
				"m.quantidade  AS qtd_saida, '' AS \"entrada\", m.tipo_movimentacao AS \"saida\", m.origem_movimentacao AS \"origem\" FROM movimentacao_estoque m ");

		SQL.append("INNER JOIN peca p ON m.peca_movimentacao = p.id ");

		SQL.append("WHERE m.tipo_movimentacao = 'SAIDA' ) TMP WHERE 1=1 ");

		if (empresa != null) {
			SQL.append("AND empresa = " + empresa.getId());

		}
		
		if (peca != null) {
			SQL.append(" AND idPeca = " + peca.getId());
			
		}

		SQL.append(" ORDER BY sequencia,dataMov ASC ");

		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		final Query query = manager.createNativeQuery(SQL.toString());
		List<MovimentacaoEstoqueVisualizacao> list = jpaResultMapper.list(query, MovimentacaoEstoqueVisualizacao.class);

		return list;

	}

}
