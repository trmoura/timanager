package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioCargo;
import br.com.timanager.dominio.DominioSimNao;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.616-0300")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile ListAttribute<Usuario, Grupo> grupos;
	public static volatile ListAttribute<Usuario, Notificacao> notificacoes;
	public static volatile SingularAttribute<Usuario, DominioCargo> cargo;
	public static volatile SingularAttribute<Usuario, Date> dataCadastro;
	public static volatile SingularAttribute<Usuario, DominioSimNao> recebeEmailChamado;
	public static volatile SingularAttribute<Usuario, byte[]> foto;
}
