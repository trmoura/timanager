package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.389-0300")
@StaticMetamodel(Garantia.class)
public class Garantia_ {
	public static volatile SingularAttribute<Garantia, Long> id;
	public static volatile SingularAttribute<Garantia, Date> dataCadastro;
	public static volatile SingularAttribute<Garantia, Date> dataInicial;
	public static volatile SingularAttribute<Garantia, Date> dataFinal;
	public static volatile SingularAttribute<Garantia, DominioSituacaoGarantia> situacao;
	public static volatile SingularAttribute<Garantia, Empresa> empresa;
	public static volatile SingularAttribute<Garantia, Usuario> usuario;
	public static volatile SingularAttribute<Garantia, String> observacao;
	public static volatile SingularAttribute<Garantia, DominioSimNao> baixada;
	public static volatile SingularAttribute<Garantia, Integer> prazoEmMeses;
	public static volatile ListAttribute<Garantia, GarantiaDetalhe> garantiaDetalhes;
	public static volatile SingularAttribute<Garantia, byte[]> documento;
}
