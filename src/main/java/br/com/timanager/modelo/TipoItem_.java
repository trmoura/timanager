package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.577-0300")
@StaticMetamodel(TipoItem.class)
public class TipoItem_ {
	public static volatile SingularAttribute<TipoItem, Long> id;
	public static volatile SingularAttribute<TipoItem, String> descricao;
	public static volatile SingularAttribute<TipoItem, Date> dataCadastro;
}
