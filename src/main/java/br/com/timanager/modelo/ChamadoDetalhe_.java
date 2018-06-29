package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.360-0300")
@StaticMetamodel(ChamadoDetalhe.class)
public class ChamadoDetalhe_ {
	public static volatile SingularAttribute<ChamadoDetalhe, Long> id;
	public static volatile SingularAttribute<ChamadoDetalhe, Date> dataInteracao;
	public static volatile SingularAttribute<ChamadoDetalhe, String> descricaoDetalhe;
	public static volatile SingularAttribute<ChamadoDetalhe, Chamado> chamado;
}
