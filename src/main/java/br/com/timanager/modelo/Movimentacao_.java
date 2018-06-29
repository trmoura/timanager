package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioOrigemMovimentacao;
import br.com.timanager.dominio.DominioTipoMovimentacao;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.466-0300")
@StaticMetamodel(Movimentacao.class)
public class Movimentacao_ {
	public static volatile SingularAttribute<Movimentacao, Long> id;
	public static volatile SingularAttribute<Movimentacao, DominioTipoMovimentacao> tipoMovimentacao;
	public static volatile SingularAttribute<Movimentacao, DominioOrigemMovimentacao> origemMovimentacao;
	public static volatile SingularAttribute<Movimentacao, Date> dataMovimentao;
	public static volatile SingularAttribute<Movimentacao, BigDecimal> quantidade;
	public static volatile SingularAttribute<Movimentacao, BigDecimal> saldoInicial;
	public static volatile SingularAttribute<Movimentacao, Empresa> empresaEntrada;
	public static volatile SingularAttribute<Movimentacao, Empresa> empresaSaida;
	public static volatile SingularAttribute<Movimentacao, Peca> pecaMovimentacao;
}
