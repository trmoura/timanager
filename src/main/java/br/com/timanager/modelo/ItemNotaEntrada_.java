package br.com.timanager.modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.437-0300")
@StaticMetamodel(ItemNotaEntrada.class)
public class ItemNotaEntrada_ {
	public static volatile SingularAttribute<ItemNotaEntrada, Long> id;
	public static volatile SingularAttribute<ItemNotaEntrada, BigDecimal> quantidade;
	public static volatile SingularAttribute<ItemNotaEntrada, BigDecimal> totalItem;
	public static volatile SingularAttribute<ItemNotaEntrada, Peca> itemPeca;
	public static volatile SingularAttribute<ItemNotaEntrada, NotaFiscalEntrada> notaFiscalEntrada;
}
