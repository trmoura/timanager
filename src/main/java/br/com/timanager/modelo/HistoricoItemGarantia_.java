package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioMotivoSaidaEntrada;
import br.com.timanager.dominio.DominioSituacaoGarantia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.425-0300")
@StaticMetamodel(HistoricoItemGarantia.class)
public class HistoricoItemGarantia_ {
	public static volatile SingularAttribute<HistoricoItemGarantia, Long> id;
	public static volatile SingularAttribute<HistoricoItemGarantia, Date> dataCadastro;
	public static volatile SingularAttribute<HistoricoItemGarantia, Date> dataSaida;
	public static volatile SingularAttribute<HistoricoItemGarantia, Date> dataRetorno;
	public static volatile SingularAttribute<HistoricoItemGarantia, Date> dataInicial;
	public static volatile SingularAttribute<HistoricoItemGarantia, Date> dataFinal;
	public static volatile SingularAttribute<HistoricoItemGarantia, DominioSituacaoGarantia> situacao;
	public static volatile SingularAttribute<HistoricoItemGarantia, DominioMotivoSaidaEntrada> motivoSaidaEntrada;
	public static volatile SingularAttribute<HistoricoItemGarantia, GarantiaDetalhe> garantiaDetalhe;
	public static volatile SingularAttribute<HistoricoItemGarantia, byte[]> documento;
	public static volatile SingularAttribute<HistoricoItemGarantia, String> numeroDocumentoServico;
	public static volatile SingularAttribute<HistoricoItemGarantia, String> observacao;
}
