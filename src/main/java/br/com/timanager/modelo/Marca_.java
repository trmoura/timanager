package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.456-0300")
@StaticMetamodel(Marca.class)
public class Marca_ {
	public static volatile SingularAttribute<Marca, Long> id;
	public static volatile SingularAttribute<Marca, String> descricao;
	public static volatile SingularAttribute<Marca, Date> dataCadastro;
}
