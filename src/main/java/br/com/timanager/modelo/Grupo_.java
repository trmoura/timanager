package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.414-0300")
@StaticMetamodel(Grupo.class)
public class Grupo_ {
	public static volatile SingularAttribute<Grupo, Long> id;
	public static volatile SingularAttribute<Grupo, String> nome;
	public static volatile SingularAttribute<Grupo, String> descricao;
	public static volatile SingularAttribute<Grupo, Date> dataCadastro;
	public static volatile ListAttribute<Grupo, Autorizacao> autorizacoes;
}
