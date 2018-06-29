package br.com.timanager.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.371-0300")
@StaticMetamodel(Empresa.class)
public class Empresa_ {
	public static volatile SingularAttribute<Empresa, Long> id;
	public static volatile SingularAttribute<Empresa, String> razaoSocial;
	public static volatile SingularAttribute<Empresa, String> nomeFantasia;
	public static volatile SingularAttribute<Empresa, String> cnpjEmpresa;
	public static volatile SingularAttribute<Empresa, String> endereco;
	public static volatile SingularAttribute<Empresa, String> fone1;
	public static volatile SingularAttribute<Empresa, String> fone2;
	public static volatile SingularAttribute<Empresa, Date> dataCadastro;
	public static volatile SingularAttribute<Empresa, byte[]> logo;
	public static volatile SingularAttribute<Empresa, String> apelido;
}
