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
import br.com.timanager.modelo.Estoque;
import br.com.timanager.modelo.Peca;

public class EstoquesDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040950598637322690L;
	
	@Inject
	private EntityManager manager;

	public Estoque porId(Long id) {
		try {
			return manager.createQuery("from Estoque where id = :id", Estoque.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Estoque porPecaEmpresa(Peca peca, Empresa empresa) {
		try {
			return manager.createQuery("from Estoque where peca = :peca and empresa = :empresa", Estoque.class)
					.setParameter("peca", peca).setParameter("empresa", empresa).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Estoque guardar(Estoque estoque) {
		return manager.merge(estoque);
	}

	public List<Estoque> buscarTodos() {
		try {
			return manager.createQuery("from Estoque ", Estoque.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean isExisteNoEstoque(Peca peca, Empresa empresa) {
		return this.porPecaEmpresa(peca, empresa) != null;
	}

	public List<MovimentacaoEstoqueVisualizacao> buscarMovimentacoesPor(Empresa empresa) {

		final StringBuilder SQL = new StringBuilder(
				" SELECT idPeca, descricao ,sequencia, dataMov, quantidade, entrada, saida FROM (");

		SQL.append(
				" SELECT p.id AS \"idPeca\", p.descricao AS \"descricao\" ,m.id AS \"sequencia\", m.data_movimentacao AS \"dataMov\", m.quantidade AS \"quantidade\",");

		SQL.append("  m.tipo_movimentacao AS \"entrada\", '' AS \"saida\" FROM movimentacao_estoque m");

		SQL.append("  INNER JOIN peca p ON m.peca_movimentacao = p.id");

		SQL.append("  WHERE m.tipo_movimentacao = 'ENTRADA'");

		SQL.append("  UNION ALL");

		SQL.append(
				" SELECT p.id AS \"idPeca\", p.descricao AS \"descricao\" ,m.id AS \"sequencia\", m.data_movimentacao AS \"dataMov\", m.quantidade AS \"quantidade\",");

		SQL.append("  '' AS \"entrada\", m.tipo_movimentacao AS \"saida\"");
		SQL.append(" FROM movimentacao_estoque m INNER JOIN peca p ON m.peca_movimentacao = p.id");
		SQL.append(" WHERE m.tipo_movimentacao = 'SAIDA'");

		SQL.append(" ) TMP ORDER BY sequencia,dataMov ASC");

		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		final Query query = manager.createNativeQuery(SQL.toString());
		List<MovimentacaoEstoqueVisualizacao> list = jpaResultMapper.list(query, MovimentacaoEstoqueVisualizacao.class);

		return list;
	}

}
