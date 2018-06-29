package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioSituacaoNotaFiscal;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.514-0300")
@StaticMetamodel(NotaFiscalEntrada.class)
public class NotaFiscalEntrada_ {
	public static volatile SingularAttribute<NotaFiscalEntrada, Long> id;
	public static volatile SingularAttribute<NotaFiscalEntrada, String> numeroNota;
	public static volatile SingularAttribute<NotaFiscalEntrada, DominioSituacaoNotaFiscal> situacao;
	public static volatile SingularAttribute<NotaFiscalEntrada, Date> dataEmissao;
	public static volatile SingularAttribute<NotaFiscalEntrada, Date> dataCadastro;
	public static volatile SingularAttribute<NotaFiscalEntrada, String> chaveNota;
	public static volatile SingularAttribute<NotaFiscalEntrada, BigDecimal> valorTotal;
	public static volatile ListAttribute<NotaFiscalEntrada, ItemNotaEntrada> itensNotaEntrada;
	public static volatile SingularAttribute<NotaFiscalEntrada, Empresa> empresa;
	public static volatile SingularAttribute<NotaFiscalEntrada, Usuario> usuario;
}
