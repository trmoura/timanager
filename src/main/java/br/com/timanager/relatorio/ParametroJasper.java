package br.com.timanager.relatorio;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import br.com.timanager.dominio.DominioRelatorio;
import br.com.timanager.dominio.DominioTipoArquivo;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;

public class ParametroJasper {

	static final String SEPARADOR = File.separator;

	static final String DIRETORIO_PADRAO_DOS_RELATORIOS = ParametroJasper.SEPARADOR + "relatorios" + ParametroJasper.SEPARADOR;

	static final String EXTENSAO_JASPER = ".jasper";

	DominioRelatorio DominioRelatorio;

	HashMap<String, Object> parametros;

	Collection<?> colecao;

	DominioTipoArquivo tipoDeArquivo;

	private ParametroJasper(final DominioRelatorio DominioRelatorio, final DominioTipoArquivo tipoDeArquivo) {

		super();
		this.DominioRelatorio = DominioRelatorio;
		this.tipoDeArquivo = tipoDeArquivo;
	}

	private ParametroJasper(final DominioRelatorio DominioRelatorio, final HashMap<String, Object> parametros, final Collection<?> colecao,
					final DominioTipoArquivo tipoDeArquivo) {

		super();
		this.DominioRelatorio = DominioRelatorio;
		this.parametros = parametros;
		this.colecao = colecao;
		this.tipoDeArquivo = tipoDeArquivo;
		this.adicionarParametrosPadroes();
	}

	private ParametroJasper(final DominioRelatorio DominioRelatorio, final Collection<?> colecao, final DominioTipoArquivo tipoDeArquivo) {

		super();
		this.DominioRelatorio = DominioRelatorio;
		this.colecao = colecao;
		this.tipoDeArquivo = tipoDeArquivo;
		this.adicionarParametrosPadroes();
	}

	public DominioRelatorio getDominioRelatorio() {

		return this.DominioRelatorio;
	}

	public HashMap<String, Object> getParametros() {

		return this.parametros;
	}

	public Collection<?> getColecao() {

		return this.colecao;
	}

	public DominioTipoArquivo getTipoDeArquivo() {

		return this.tipoDeArquivo;
	}

	public static ParametroJasper criarPDF(final DominioRelatorio DominioRelatorio, final HashMap<String, Object> parametros,
					final Collection<?> colecao) {

		return new ParametroJasper(DominioRelatorio, parametros, colecao, DominioTipoArquivo.PDF);
	}

	public static ParametroJasper criarPDF(final DominioRelatorio DominioRelatorio, final HashMap<String, Object> parametros) {

		return new ParametroJasper(DominioRelatorio, parametros, Arrays.asList(""), DominioTipoArquivo.PDF);
	}

	public static ParametroJasper criarPDF(final DominioRelatorio DominioRelatorio) {

		return new ParametroJasper(DominioRelatorio, DominioTipoArquivo.PDF);
	}

	public static ParametroJasper criarPDF(final DominioRelatorio DominioRelatorio, final Collection<?> colecao) {

		return new ParametroJasper(DominioRelatorio, colecao, DominioTipoArquivo.PDF);
	}

	public static ParametroJasper criarXLS(final DominioRelatorio DominioRelatorio, final HashMap<String, Object> parametros,
					final Collection<?> colecao) {

		return new ParametroJasper(DominioRelatorio, parametros, colecao, DominioTipoArquivo.XLS);
	}

	public static ParametroJasper criarXLS(final DominioRelatorio DominioRelatorio, final Collection<?> colecao) {

		return new ParametroJasper(DominioRelatorio, colecao, DominioTipoArquivo.XLS);
	}

	private void adicionarParametrosPadroes() {

		if (this.parametros == null) {
			this.parametros = new HashMap<String, Object>();
		}

		this.parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		this.parametros.put(JRParameter.REPORT_VIRTUALIZER, new JRGzipVirtualizer(100));
	}

	private String obterCaminhoReal(final String caminhoRelativo) {

		final FacesContext context = FacesContext.getCurrentInstance();
		final ServletContext sctx = (ServletContext) context.getExternalContext().getContext();
		return sctx.getRealPath(caminhoRelativo);
	}

	private String getExtensaoDoArquivo() {

		return this.getTipoDeArquivo().getExtensao();
	}

	private String getExtensaoDoJasper() {

		return ParametroJasper.EXTENSAO_JASPER;
	}

	public String getNomeMaisExtensaoDoArquivo() {

		return this.getDominioRelatorio().getNomeDoArquivo() + this.getExtensaoDoArquivo();
	}

	private String getNomeMaisExtensaoDoJasper() {

		return this.getDominioRelatorio().getNomeDoJasper() + this.getExtensaoDoJasper();
	}

	private String obterCaminhoRelativoDoArquivoJasper() {

		return ParametroJasper.DIRETORIO_PADRAO_DOS_RELATORIOS + this.getNomeMaisExtensaoDoJasper();
	}

	public String obterCaminhoRealDoArquivoJasper() {

		return this.obterCaminhoReal(this.obterCaminhoRelativoDoArquivoJasper());
	}

}