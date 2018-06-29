package br.com.timanager.modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.379-0300")
@StaticMetamodel(Estoque.class)
public class Estoque_ {
	public static volatile SingularAttribute<Estoque, Long> id;
	public static volatile SingularAttribute<Estoque, Date> dataEntradaInicial;
	public static volatile SingularAttribute<Estoque, BigDecimal> quantidade;
	public static volatile SingularAttribute<Estoque, Peca> peca;
	public static volatile SingularAttribute<Estoque, Empresa> empresa;
}
