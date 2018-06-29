package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioParametroSistema;
import br.com.timanager.dominio.DominioSimNao;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.543-0300")
@StaticMetamodel(ParametroSistema.class)
public class ParametroSistema_ {
	public static volatile SingularAttribute<ParametroSistema, DominioParametroSistema> nomeParametro;
	public static volatile SingularAttribute<ParametroSistema, String> valor;
	public static volatile SingularAttribute<ParametroSistema, DominioSimNao> visivel;
}
