package br.com.timanager.modelo;

import br.com.timanager.dominio.DominioCondicao;
import br.com.timanager.dominio.DominioSimNao;
import br.com.timanager.dominio.DominioSituacaoPeca;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-06-27T22:11:35.555-0300")
@StaticMetamodel(Peca.class)
public class Peca_ {
	public static volatile SingularAttribute<Peca, Long> id;
	public static volatile SingularAttribute<Peca, String> descricao;
	public static volatile SingularAttribute<Peca, byte[]> documento;
	public static volatile SingularAttribute<Peca, String> numeroPatrimonio;
	public static volatile SingularAttribute<Peca, String> numeroSerie;
	public static volatile SingularAttribute<Peca, String> modelo;
	public static volatile SingularAttribute<Peca, String> numeroDocumento;
	public static volatile SingularAttribute<Peca, BigDecimal> valor;
	public static volatile SingularAttribute<Peca, DominioCondicao> condicao;
	public static volatile SingularAttribute<Peca, DominioSituacaoPeca> situacao;
	public static volatile SingularAttribute<Peca, Date> dataCadastro;
	public static volatile SingularAttribute<Peca, Date> dataFuncionamento;
	public static volatile SingularAttribute<Peca, Date> dataDocumento;
	public static volatile SingularAttribute<Peca, String> observacao;
	public static volatile SingularAttribute<Peca, String> qrcode;
	public static volatile SingularAttribute<Peca, Marca> marca;
	public static volatile SingularAttribute<Peca, TipoItem> tipo;
	public static volatile SingularAttribute<Peca, Setor> setor;
	public static volatile SingularAttribute<Peca, Empresa> empresa;
	public static volatile SingularAttribute<Peca, DominioSimNao> emManutencao;
	public static volatile SingularAttribute<Peca, Integer> diasFuncionando;
}
