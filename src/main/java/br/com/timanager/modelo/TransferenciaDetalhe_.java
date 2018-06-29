package br.com.timanager.modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.606-0300")
@StaticMetamodel(TransferenciaDetalhe.class)
public class TransferenciaDetalhe_ {
	public static volatile SingularAttribute<TransferenciaDetalhe, Long> id;
	public static volatile SingularAttribute<TransferenciaDetalhe, Peca> pecaTranferencia;
	public static volatile SingularAttribute<TransferenciaDetalhe, BigDecimal> quantidade;
	public static volatile SingularAttribute<TransferenciaDetalhe, BigDecimal> quantidadeAtualDia;
	public static volatile SingularAttribute<TransferenciaDetalhe, BigDecimal> totalQuantidade;
	public static volatile SingularAttribute<TransferenciaDetalhe, Transferencia> transferencia;
}
