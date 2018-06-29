package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.593-0300")
@StaticMetamodel(Transferencia.class)
public class Transferencia_ {
	public static volatile SingularAttribute<Transferencia, Long> id;
	public static volatile SingularAttribute<Transferencia, Date> dataCadastro;
	public static volatile SingularAttribute<Transferencia, Empresa> empresaEntrada;
	public static volatile SingularAttribute<Transferencia, Empresa> empresaSaida;
	public static volatile SingularAttribute<Transferencia, String> observacao;
	public static volatile ListAttribute<Transferencia, TransferenciaDetalhe> transferenciaDetalhes;
}
