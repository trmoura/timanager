package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioSituacaoChamado;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.344-0300")
@StaticMetamodel(Chamado.class)
public class Chamado_ {
	public static volatile SingularAttribute<Chamado, Long> id;
	public static volatile SingularAttribute<Chamado, Date> dataAbertura;
	public static volatile SingularAttribute<Chamado, Date> dataEncerramento;
	public static volatile SingularAttribute<Chamado, Empresa> empresa;
	public static volatile SingularAttribute<Chamado, Setor> setor;
	public static volatile SingularAttribute<Chamado, Usuario> usuario;
	public static volatile SingularAttribute<Chamado, String> descricaoChamado;
	public static volatile SingularAttribute<Chamado, String> parecer;
	public static volatile SingularAttribute<Chamado, DominioSituacaoChamado> situacaoChamado;
	public static volatile SingularAttribute<Chamado, Usuario> tecnico;
	public static volatile ListAttribute<Chamado, ChamadoDetalhe> chamadoDetalhes;
}
