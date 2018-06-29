package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioSimNao;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.529-0300")
@StaticMetamodel(Notificacao.class)
public class Notificacao_ {
	public static volatile SingularAttribute<Notificacao, Long> id;
	public static volatile SingularAttribute<Notificacao, String> titulo;
	public static volatile SingularAttribute<Notificacao, String> mensagem;
	public static volatile SingularAttribute<Notificacao, Date> dataNotificacao;
	public static volatile SingularAttribute<Notificacao, DominioSimNao> lida;
	public static volatile SingularAttribute<Notificacao, Usuario> usuario;
}
