package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioSituacaoItem;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.399-0300")
@StaticMetamodel(GarantiaDetalhe.class)
public class GarantiaDetalhe_ {
	public static volatile SingularAttribute<GarantiaDetalhe, Long> id;
	public static volatile SingularAttribute<GarantiaDetalhe, Peca> peca;
	public static volatile SingularAttribute<GarantiaDetalhe, Garantia> garantia;
	public static volatile SingularAttribute<GarantiaDetalhe, DominioSituacaoItem> situacaoItemGarantia;
	public static volatile ListAttribute<GarantiaDetalhe, HistoricoItemGarantia> historicosItem;
}
