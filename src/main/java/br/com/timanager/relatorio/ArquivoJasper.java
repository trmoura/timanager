package br.com.timanager.relatorio;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.timanager.DAO.ParametrosDAO;
import br.com.timanager.cdi.CDIServiceLocator;
import br.com.timanager.dominio.DominioTipoArquivo;
import br.com.timanager.interfaces.IArquivo;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ArquivoJasper implements IArquivo {

	private final String nome;

	private final byte[] conteudo;

	private Connection con;

	private final DominioTipoArquivo tipoDeArquivo;

	private ParametrosDAO parametrosDAO;
	
	private String teste;

	public ArquivoJasper(final ParametroJasper parametroJasper) {

		super();

		this.nome = parametroJasper.getNomeMaisExtensaoDoArquivo();
		this.conteudo = this.criarArquivoJasper(parametroJasper);
		this.tipoDeArquivo = parametroJasper.getTipoDeArquivo();
	}

	public ArquivoJasper(final ParametroJasper parametroJasper, final byte[] conteudo) {

		super();
		this.nome = parametroJasper.getNomeMaisExtensaoDoArquivo();
		this.conteudo = conteudo;
		this.tipoDeArquivo = parametroJasper.getTipoDeArquivo();
	}

	public ArquivoJasper(final ParametroJasper parametroJasper, final String nome) {

		super();

		this.nome = nome;
		this.conteudo = this.criarArquivoJasper(parametroJasper);
		this.tipoDeArquivo = parametroJasper.getTipoDeArquivo();
	}

	public ArquivoJasper(final ParametroJasper parametroJasper, final String nome, final String tipoDatasource) {

		super();
		
		this.parametrosDAO = CDIServiceLocator.getBean(ParametrosDAO.class);
		this.nome = nome;
		this.conteudo = this.criarArquivoJasperBD(parametroJasper);
		this.tipoDeArquivo = parametroJasper.getTipoDeArquivo();
	}

	public ArquivoJasper(final ParametroJasper parametroJasper, final byte[] conteudo, final String nome) {

		super();
		this.nome = nome;
		this.conteudo = conteudo;
		this.tipoDeArquivo = parametroJasper.getTipoDeArquivo();
	}

	@Override
	public String getNome() {

		return this.nome;
	}

	@Override
	public byte[] getConteudo() {

		return this.conteudo;
	}

	@Override
	public DominioTipoArquivo getTipoDeArquivo() {

		return this.tipoDeArquivo;
	}

	private byte[] criarArquivoJasper(final ParametroJasper parametroJasper) {

		try {

			final JasperPrint jasperPrint = this.jasperPrint(parametroJasper);

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();

			final JRAbstractExporter exporter = this.exporter(parametroJasper);
			// JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();

			return baos.toByteArray();

		} catch (final JRException e) {
			e.printStackTrace();
			new RelatorioJasperException();
		} catch (final Exception e) {
			e.printStackTrace();
			new RelatorioJasperException();
		}
		return this.conteudo;
	}

	private byte[] criarArquivoJasperBD(final ParametroJasper parametroJasper) {

		try {

			final JasperPrint jasperPrint = this.jasperPrintBD(parametroJasper);

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();

			final JRAbstractExporter exporter = this.exporter(parametroJasper);
			// JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();

			return baos.toByteArray();

		} catch (final JRException e) {
			e.printStackTrace();
			new RelatorioJasperException();
		} catch (final Exception e) {
			e.printStackTrace();
			new RelatorioJasperException();
		}
		return this.conteudo;
	}

	private JasperPrint jasperPrint(final ParametroJasper parametroJasper) throws JRException {

		return JasperFillManager.fillReport(parametroJasper.obterCaminhoRealDoArquivoJasper(),
				parametroJasper.getParametros(), this.dataSource(parametroJasper));
	}

	private JasperPrint jasperPrintBD(final ParametroJasper parametroJasper) throws JRException {

		return JasperFillManager.fillReport(parametroJasper.obterCaminhoRealDoArquivoJasper(),
				parametroJasper.getParametros(), this.getConexao());
	}

	private Connection getConexao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(this.getCaminhoBancoDeDados(), this.getUsuarioBancoDeDados(),
					this.getSenhaBancoDeDados());
			return con;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private String getSenhaBancoDeDados() {
		if (parametrosDAO.getParametroLocalHost().getValor().equals("S")) {
			return parametrosDAO.getParametroSenhaLocal().getValor();
		} else {
			return parametrosDAO.getParametroSenhaProducao().getValor();
		}
	}

	private String getUsuarioBancoDeDados() {
		if (parametrosDAO.getParametroLocalHost().getValor().equals("S")) {
			return parametrosDAO.getParametroUsuarioLocal().getValor();
		} else {
			return parametrosDAO.getParametroUsuarioProducao().getValor();
		}
	}

	private String getCaminhoBancoDeDados() {
		if (parametrosDAO.getParametroLocalHost().getValor().equals("S")) {
			return parametrosDAO.getParametroCaminhoBancoLocal().getValor();
		} else {
			return parametrosDAO.getParametroCaminhoBancoProducao().getValor();
		}

	}

	private JRBeanCollectionDataSource dataSource(final ParametroJasper parametroJasper) {

		return new JRBeanCollectionDataSource(parametroJasper.getColecao());
	}

	private JRAbstractExporter exporter(final ParametroJasper parametroJasper) {

		switch (parametroJasper.getTipoDeArquivo()) {
		case PDF:
			return new JRPdfExporter();
		case XLS:
			return new JRXlsExporter();

		default:
			return null;
		}

	}

}